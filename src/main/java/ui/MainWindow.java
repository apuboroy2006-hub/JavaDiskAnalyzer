package ui;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;

import model.FolderInfo;
import model.ScanResult;
import service.ExportService;
import service.ScanService;
import service.SearchService;
import ui.dialogs.ErrorDialog;
import ui.dialogs.ExportDialog;
import ui.layout.MainLayout;
import utils.FileUtils;
import utils.SizeFormatter;
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

    private void search() {

    if (currentRoot == null) {
        ErrorDialog.show(this, "Scan a folder first.");
        return;
    }

    String query = layout.toolbar.search.getText().trim();

    if (query.isBlank()) {
        ErrorDialog.show(this, "Enter something to search.");
        return;
    }

    SearchService searchService = new SearchService();

    var results = searchService.search(
            currentRoot.getPath(),
            query
    );

    if (results.isEmpty()) {

        JOptionPane.showMessageDialog(
                this,
                "No matches found for:\n" + query,
                "Search Results (0)",
                JOptionPane.INFORMATION_MESSAGE
        );

        return;
    }

    StringBuilder message = new StringBuilder();

    int displayLimit = Math.min(results.size(), 100);

    for (int i = 0; i < displayLimit; i++) {

        Path path = results.get(i);

        String type = Files.isDirectory(path)
                ? "[Folder]"
                : "[File]";

        message.append(type)
               .append("  ")
               .append(path)
               .append("\n");
    }

    if (results.size() > 100) {

        message.append("\n")
               .append("Showing first 100 results out of ")
               .append(results.size())
               .append(" matches.");
    }

    JTextArea area = new JTextArea(message.toString());

    area.setEditable(false);
    area.setLineWrap(false);

    JScrollPane scrollPane = new JScrollPane(area);

    scrollPane.setPreferredSize(
            new Dimension(850, 500)
    );

    JOptionPane.showMessageDialog(
            this,
            scrollPane,
            "Search Results (" + results.size() + ")",
            JOptionPane.INFORMATION_MESSAGE
    );
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
