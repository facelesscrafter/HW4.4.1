import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PoliceStation {
    public static Scanner scanner=new Scanner(System.in);
    public static int operationCode=0;
    public static void main(String[] args) {
        Dossier[] dossiers=new Dossier[3];
        for(int i=0;i<3;i++)dossiers[i]=new Dossier();
        int n=0;
        scanner.useDelimiter("\n");
        do {
            n=getOperation();
            switch (operationCode){
                case 1:
                    dossiers[n-1].setDossier(getInfo());
                    break;
                case 2:
                    if(!dossiers[n-1].getEmpty())System.out.println(dossiers[n-1].getDossier());
                    else System.out.println("Досье пустое");
                    break;
            }
        }while(operationCode!=0);
        scanner.close();
    }

    static int getOperation(){
        String operation = "pusto";
        System.out.println("Введите команду write N, read N (где N - число от 1 до 3) или q");
        int n=0;
        try {
            operation = scanner.next();
        } catch (Exception e) {
            System.out.println("Вы ввели что-то уж совсем не то. Программа будет завершена");
            operationCode=0;
        }

        if (operation.matches("write [1-3]")){
            n=Character.getNumericValue(operation.charAt(6));
            operationCode=1;
        }
        else if (operation.matches("read [1-3]")){
            n=Character.getNumericValue(operation.charAt(5));
            operationCode=2;
        }
        else if (operation.matches("q"))operationCode = 0;
        else {
            operationCode = 3;
            System.out.println("Неверное значение!");
        }
        return n;
    }

    static String getInfo(){
        String info="pusto";
        System.out.println("Информация для ввода?");
        try {
            info = scanner.next();
        } catch (Exception e) {
            System.out.println("Вы ввели что-то уж совсем не то. Программа будет завершена");
            operationCode=0;
        }
        return info;
    }
}

class Dossier{
    public static int dossierCount=0;
    private int dossierNum;
    private String name;
    private String job;
    private String place;
    private StringBuilder officer;
    private boolean isEmpty=true;

    public Dossier(){
        dossierCount++;
        this.dossierNum=dossierCount;
        this.isEmpty=true;
        this.name = "-";
        this.job="-";
        this.place="-";
    }


    public boolean getEmpty() {
        return this.isEmpty;
    }
    public String getDossier() {
        return "Номер досье"+dossierNum+"\nИмя: "+name+"\nРод занятий: "+job+"\nМестоположение: "+place+"\nОфицеры, имевшие доступ к досье: "+officer.toString();
    }

    public void setDossier(String info) {
        String name,job,place,officer;
      //  if (info.matches("read [1-3]"))
        name=this.parseTag(info,"name");
        job=this.parseTag(info,"job");
        place=this.parseTag(info,"place");
        officer=this.parseTag(info,"officer");
        if(officer.equals("-"))System.out.println("Ошибка! Имя офицера обязательно должно быть заполнено!");
        else if(name.equals("-")&&job.equals("-")&&place.equals("-"))System.out.println("Ошибка! Нечего заполнять!");
        else if(this.getEmpty()){
                this.name=name;
                this.job=job;
                this.place=place;
                this.officer=new StringBuilder(officer);
                this.isEmpty=false;
                System.out.println("Данные успешно внесены\n");
            }
//not empty
        else{
            if(this.name.equals("-"))this.name=name;
            if(this.job.equals("-"))this.job=job;
            if(this.place.equals("-"))this.place=place;
            this.officer.append(", "+officer);
            System.out.println("Данные успешно внесены\n");
        }

    }
    String parseTag(String info,String value){
        String tag="-";
        Pattern pattern = Pattern.compile("<"+value+">.*?<\\/"+value+">");
        Matcher matcher = pattern.matcher(info);
        if(matcher.find()) tag=info.substring(matcher.start()+value.length()+2, matcher.end()-value.length()-3);;
        return tag;
    }
    void setJob(String job) {
        this.job = job;
    }

    void setPlace(String place) {
        this.place = place;
    }

    public void setOfficer(StringBuilder officer) {
        this.officer = officer;
    }
}
//<name>Лютик</name><job>Менестрель</job><place>Идет за Плотвой</place><officer>Эмиэль Регис</officer>
//<name>Лютик</name><job>-</job><place>Идет за Плотвой</place><officer>Эмиэль Регис</officer>
//<name>Золтан</name><job>Графоман</job><place>Тюрьма</place><officer>Фродо</officer>