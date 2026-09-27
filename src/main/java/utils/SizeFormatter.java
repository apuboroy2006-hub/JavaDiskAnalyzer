package utils;
public final class SizeFormatter {
    private SizeFormatter(){}
    public static String format(long b){
        if(b<1024)return b+" B";
        double v=b; String[] u={"KB","MB","GB","TB","PB"}; int i=-1;
        do{v/=1024;i++;}while(v>=1024&&i<u.length-1);
        return String.format("%.2f %s",v,u[i]);
    }
}
