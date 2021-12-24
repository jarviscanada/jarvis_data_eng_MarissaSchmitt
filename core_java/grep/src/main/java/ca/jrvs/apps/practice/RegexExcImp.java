package ca.jrvs.apps.practice;

public class RegexExcImp implements RegexExc{

    public static void main(String[] args) {
        RegexExcImp regex = new RegexExcImp();
        System.out.println(regex.matchJpeg("test.jpg"));
    }

    public boolean matchJpeg(String filename)
    {
        if(filename.matches("/(?i)(.jpg|.jpeg)$/gm"))
        {
            return true;
        }
        return false;
    }

    public boolean matchIP(String ip)
    {
        if(ip.matches("/^\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}/gm"))
        {
            return true;
        }
        return false;
    }

    public boolean isLineEmpty(String line)
    {
        //using regex to check if the line is empty (including whitespace
        if(line.matches("/^\\s*$/gm"))
        {
            return true;
        }
        return false;
    }
}
