public class Utils {

    public static String getDobaOdepisovaniFormated(int dobaOdepisovani){
        StringBuilder stringBuilder = new StringBuilder().append(dobaOdepisovani).append(" ");
        switch (dobaOdepisovani){
            case 1:
                stringBuilder.append("rok");
                break;
            case 2:
            case 3:
            case 4:
                stringBuilder.append("roky");
                break;
            default:
                stringBuilder.append("let");
                break;
        }
        return stringBuilder.toString();
    }

    public static void log(String message){
        System.out.println(message);
    }

    public static String decimation(long number, String character){
        String decimal = flipText(String.valueOf(number));
        int size = 0;
        StringBuilder data = new StringBuilder();
        for(String s : decimal.split("")){
            size++;
            data.append(s);
            if(size >= 3){
                size = 0;
                data.append(character);
            }
        }
        if(data.toString().split("")[data.toString().split("").length-1].equals(character)){
            data = new StringBuilder(data.substring(0, data.length() - 1));
        }
        return flipText(data.toString());
    }

    public static String flipText(String m){
        StringBuilder str = new StringBuilder();
        for(int i = m.length() - 1; i > 0; i--) {
            str.append(m.charAt(i));
        }
        str.append(m.charAt(0));
        return str.toString();
    }
}
