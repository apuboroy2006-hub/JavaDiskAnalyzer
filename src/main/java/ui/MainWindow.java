package ui;
import javax.swing.*;
import java.awt.*;
import java.nio.file.*;
import java.awt.event.*;
import model.*;
import service.*;
import ui.dialogs.*;
import ui.layout.MainLayout;
import utils.SizeFormatter;
import utils.FileUtils;
import worker.ScanWorker;

public class MainWindow extends JFrame {
    private final MainLayout layout=new MainLayout();
    private final ScanService scanService=new ScanService();
    private ScanWorker worker;
    private FolderInfo currentRoot;

    public MainWindow(){
        setTitle("Java Disk Analyzer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200,750);setLocationRelativeTo(null);
        add(layout);

        layout.toolbar.select.addActionListener(e->chooseFolder());
        layout.toolbar.scan.addActionListener(e->scanDrive());
        layout.toolbar.stop.addActionListener(e->{if(worker!=null)worker.cancel(true);});
        layout.toolbar.export.addActionListener(e->exportReport());
        layout.toolbar.search.addActionListener(e->search());

        layout.tree.tree().addTreeSelectionListener(e->{
            FolderInfo f=layout.tree.selectedFolder();
            if(f!=null)layout.table.showFolder(f);
        });

        layout.table.table().addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
                if(e.getClickCount()==2){
                    Path p=layout.table.selectedPath();
                    if(p!=null&&Files.isDirectory(p))try{FileUtils.open(p);}catch(Exception ex){ErrorDialog.show(MainWindow.this,ex.getMessage());}
                }
            }
        });
    }

    private void chooseFolder(){
        JFileChooser c=new JFileChooser();c.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if(c.showOpenDialog(this)==JFileChooser.APPROVE_OPTION)startScan(c.getSelectedFile().toPath());
    }

    private void scanDrive(){
        Object o=layout.toolbar.drives.getSelectedItem();
        if(o instanceof Path p)startScan(p);
    }

    private void startScan(Path root){
        if(!Files.isDirectory(root)){ErrorDialog.show(this,"Invalid folder.");return;}
        if(worker!=null&&!worker.isDone())worker.cancel(true);
        layout.progress.bar.setIndeterminate(true);
        layout.progress.status.setText("Scanning: "+root);
        worker=new ScanWorker(root,scanService,p->layout.progress.status.setText("Scanning: "+p.getFileName()));
        worker.addPropertyChangeListener(e->{
            if("state".equals(e.getPropertyName())&&e.getNewValue()==SwingWorker.StateValue.DONE){
                try{
                    if(worker.isCancelled()){layout.progress.status.setText("Cancelled");return;}
                    ScanResult r=worker.get();currentRoot=r.root();
                    layout.tree.setRoot(currentRoot);layout.table.showFolder(currentRoot);
                    layout.usage.update(r.bytes(),r.files(),r.folders());
                    layout.progress.status.setText("Done — "+SizeFormatter.format(r.bytes())+" — Errors: "+r.errors());
                }catch(Exception ex){ErrorDialog.show(this,ex.getMessage());}
                finally{layout.progress.bar.setIndeterminate(false);}
            }
        });
        worker.execute();
    }

    private void search(){
        if(currentRoot==null||layout.toolbar.search.getText().isBlank())return;
        var results=new SearchService().search(currentRoot.getPath(),layout.toolbar.search.getText());
        JOptionPane.showMessageDialog(this,
            results.isEmpty()?"No matches.":String.join("\n",results.stream().limit(50).map(Path::toString).toList()),
            "Search Results ("+results.size()+")",JOptionPane.INFORMATION_MESSAGE);
    }

    private void exportReport(){
        if(currentRoot==null){ErrorDialog.show(this,"Scan a folder first.");return;}
        Path target=ExportDialog.choose(this);if(target==null)return;
        String[] opts={"CSV","JSON","HTML"};
        String type=(String)JOptionPane.showInputDialog(this,"Format:","Export",
            JOptionPane.QUESTION_MESSAGE,null,opts,opts[0]);
        if(type==null)return;
        try{
            ExportService s=new ExportService();
            switch(type){case "CSV"->s.csv(currentRoot,target);case "JSON"->s.json(currentRoot,target);case "HTML"->s.html(currentRoot,target);}
            JOptionPane.showMessageDialog(this,"Export completed.");
        }catch(Exception ex){ErrorDialog.show(this,ex.getMessage());}
    }
}
