package task11;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import utilities.TestBase;

import java.io.*;

public class Task11 extends TestBase {
    //    -İki tane test methodu oluşturalım
    //    -İlkinde masaüstünde java ile data.xlsx isimli bir dosya oluşturalım
    //    -Birinci satır birinci hücreye email ve ikinci hücreye password
    //    -ikinci satır birinci hücreye evren.techproed@gmail.com ve ikinci hücreye asdfgh yazdıralım
    //    -Dosyayı kaydedelim
    //    -İkinci methodda https://www.bluerentalcars.com/ adresine gidip
    //    -Excel dosyasından aldığımız kullanıcı bilgileri ile login olalım
    //    -Login olduğumuzu doğrulayalım
   private static String ortakdosya = "C:\\Users\\ihsan\\OneDrive\\Masaüstü\\Yeni klasör\\data.xlsx";

    @Test
    public void task11() throws IOException {

        // java ile data.xlsx dosyasi olusturalim

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("sheet01");

        // satır olusturalim

        Row row = sheet.createRow(0);
        //hucre olustur ve deger ata

        Cell cell =row.createCell(0);
        cell.setCellValue("email");
        Cell cell1 =row.createCell(1);
        cell1.setCellValue("password");

        // ikinci satiri olustur
        Row row1= sheet.createRow(1);
        Cell cell2 = row1.createCell(0);
        cell2.setCellValue("evren.techproed@gmail.com");
       Cell cell3 =  row1.createCell(1);
       cell3.setCellValue("asdfgh");

       // dosyayı kaydetmek icin fileoutput kullan

        FileOutputStream filekayitli = new FileOutputStream(ortakdosya);
        workbook.write(filekayitli);

        // kullanimlari kapat

        filekayitli.close();
        workbook.close();

        System.out.println("dosyasi " +ortakdosya+ " konumunda başariyla olusturulmustur.");



    }

    @Test
    public void task1102() throws IOException {

        //    -İkinci methodda https://www.bluerentalcars.com/ adresine gidip
        //    -Excel dosyasından aldığımız kullanıcı bilgileri ile login olalım
        //    -Login olduğumuzu doğrulayalım

        driver.get("https://www.bluerentalcars.com/");
        driver.findElement(By.xpath("//a[@class='btn btn-primary btn-sm']")).click();
        bekle(2);

        WebElement emailkutusu = driver.findElement(By.xpath("//input[@id='formBasicEmail']"));

       WebElement passwordkutusu =  driver.findElement(By.xpath("//input[@id='formBasicPassword']"));

        FileInputStream fileInputStream = new FileInputStream(new File(ortakdosya));
        Workbook workbook = new XSSFWorkbook(fileInputStream);
        Sheet sheet = workbook.getSheetAt(0);

       String email= sheet.getRow(1).getCell(0).getStringCellValue();
       String password =  sheet.getRow(1).getCell(1).getStringCellValue();

       emailkutusu.sendKeys(email);
        passwordkutusu.sendKeys(password);
        bekle(2);

        WebElement loginbutton = driver.findElement(By.xpath("//button[@type='submit']"));
        loginbutton.click();

        WebElement iscontains =driver.findElement(By.xpath("//button[@id='dropdown-basic-button']"));
        String sonuc = iscontains.getText();
        Assert.assertTrue(sonuc.contains("Erol Evren"));



    }
}
