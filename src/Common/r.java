package Common;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Pattern;
@SuppressWarnings({"WeakerAccess","FinalStaticMethod"})
public class r//To be merged with the r class in the future...
{

    /**
     * Created by Ryan B on 2/7/16.
     * <p>
     * This is a class that can be easily extended, or copied and pasted into, any other class.
     * It contains many shorthand notations for otherwise annoyingly verbose methods, and offers
     * additional functionality such as complex mathematical operations and inverse hyperbolic trig.
     */
//region UML (May be outdated)
//    ┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓
//    ┃ ryansappframework.r                                                   ┃
//    ┠───────────────────────────────────────────────────────────────────────┨
//    ┃ - Complex : class                                                     ┃
//    ┃ - DEG_PER_RAD : double                                                ┃
//    ┃ - DEG_TO_RAD : double                                                 ┃
//    ┃ - E : double                                                          ┃
//    ┃ - PI : double                                                         ┃
//    ┃ - RAD_PER_DEG : double                                                ┃
//    ┃ - RAD_TO_DEG : double                                                 ┃
//    ┃ - TWO_PI : double                                                     ┃
//    ┠┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┈┨
//    ┃ + abs(x:Complex) : double                                             ┃
//    ┃ + abs(x:double) : double                                              ┃
//    ┃ + acos(x:double) : double                                             ┃
//    ┃ + acosh(x:double) : double                                            ┃
//    ┃ + acot(x:double) : double                                             ┃
//    ┃ + acoth(x:double) : double                                            ┃
//    ┃ + acsc(x:double) : double                                             ┃
//    ┃ + acsch(x:double) : double                                            ┃
//    ┃ + add(a:double, b:double, x:double, y:double) : Complex               ┃
//    ┃ + add(X:Complex, Y:Complex) : Complex                                 ┃
//    ┃ + angleOf(x:double, y:double) : double                                ┃
//    ┃ + arg(x:Complex) : double                                             ┃
//    ┃ + asec(x:double) : double                                             ┃
//    ┃ + asech(x:double) : double                                            ┃
//    ┃ + asin(x:double) : double                                             ┃
//    ┃ + asinh(x:double) : double                                            ┃
//    ┃ + atan(x:double) : double                                             ┃
//    ┃ + atanh(x:double) : double                                            ┃
//    ┃ + avg() : double                                                      ┃
//    ┃ + avg() : double                                                      ┃
//    ┃ + Boolean(x:int) : boolean                                            ┃
//    ┃ + booleanScan() : boolean                                             ┃
//    ┃ + ceil(x:double) : long                                               ┃
//    ┃ + chance(Probability:double) : boolean                                ┃
//    ┃ + complex(real:double, imag:double) : Complex                         ┃
//    ┃ + cos(x:double) : double                                              ┃
//    ┃ + cosh(x:double) : double                                             ┃
//    ┃ + cot(x:double) : double                                              ┃
//    ┃ + coth(x:double) : double                                             ┃
//    ┃ + csc(x:double) : double                                              ┃
//    ┃ + csch(x:double) : double                                             ┃
//    ┃ + delay(delayDurationInSeconds:double) : void                         ┃
//    ┃ + delayInMillis(delayDurationInMilliseconds:long) : void              ┃
//    ┃ + distance(X:double, Y:double, x:double, y:double) : double           ┃
//    ┃ + div(a:double, b:double, x:double, y:double) : Complex               ┃
//    ┃ + div(X:Complex, Y:Complex) : Complex                                 ┃
//    ┃ + Double(Input:String) : double                                       ┃
//    ┃ + doubleArray() : double[]                                            ┃
//    ┃ + doubleScan() : double                                               ┃
//    ┃ + exp(a:double, b:double) : Complex                                   ┃
//    ┃ + exp(X:Complex) : Complex                                            ┃
//    ┃ + exp(x:double) : double                                              ┃
//    ┃ + factorial(x:long) : long                                            ┃
//    ┃ + floatScan() : float                                                 ┃
//    ┃ + floor(x:double) : long                                              ┃
//    ┃ + im(imag:double) : Complex                                           ┃
//    ┃ + im(x:Complex) : double                                              ┃
//    ┃ + imag(imag:double) : Complex                                         ┃
//    ┃ + imag(x:Complex) : double                                            ┃
//    ┃ + Int(Input:String) : double                                          ┃
//    ┃ + Int(x:boolean) : int                                                ┃
//    ┃ + intScan() : int                                                     ┃
//    ┃ + ln(a:double, b:double) : Complex                                    ┃
//    ┃ + ln(X:Complex) : Complex                                             ┃
//    ┃ + ln(x:double) : double                                               ┃
//    ┃ + log(Base:Complex, X:Complex) : Complex                              ┃
//    ┃ + log(Base_a:double, Base_b:double, X_x:double, X_y:double) : Complex ┃
//    ┃ + Long(Input:String) : double                                         ┃
//    ┃ + longScan() : long                                                   ┃
//    ┃ + mag(x:double, y:double) : double                                    ┃
//    ┃ + max(x:double, y:double) : double                                    ┃
//    ┃ + millis() : long                                                     ┃
//    ┃ + min(x:double, y:double) : double                                    ┃
//    ┃ + mouseX() : int                                                      ┃
//    ┃ + mouseY() : int                                                      ┃
//    ┃ + mult(a:double, b:double, x:double, y:double) : Complex              ┃
//    ┃ + mult(X:Complex, Y:Complex) : Complex                                ┃
//    ┃ + OpenWebsite(URL:String) : void                                      ┃
//    ┃ + pow(a:double, b:double, x:double, y:double) : Complex               ┃
//    ┃ + pow(X:Complex, Y:Complex) : Complex                                 ┃
//    ┃ + print(message:BigDecimal) : void                                    ┃
//    ┃ + print(message:boolean) : void                                       ┃
//    ┃ + print(message:char) : void                                          ┃
//    ┃ + print(message:double) : void                                        ┃
//    ┃ + print(message:long) : void                                          ┃
//    ┃ + print(message:String) : void                                        ┃
//    ┃ + println() : void                                                    ┃
//    ┃ + println(message:BigDecimal) : void                                  ┃
//    ┃ + println(message:boolean) : void                                     ┃
//    ┃ + println(message:char) : void                                        ┃
//    ┃ + println(message:double) : void                                      ┃
//    ┃ + println(message:long) : void                                        ┃
//    ┃ + println(message:String) : void                                      ┃
//    ┃ + random(x:double) : double                                           ┃
//    ┃ + randomInt(x:double) : int                                           ┃
//    ┃ + re(real:double) : Complex                                           ┃
//    ┃ + re(x:Complex) : double                                              ┃
//    ┃ + ReadFile(FilePathNamethrows:String) : String                        ┃
//    ┃ + real(real:double) : Complex                                         ┃
//    ┃ + real(x:Complex) : double                                            ┃
//    ┃ + round(x:double) : long                                              ┃
//    ┃ + scan() : String                                                     ┃
//    ┃ + scan(Message:String) : String                                       ┃
//    ┃ + sec(x:double) : double                                              ┃
//    ┃ + sech(x:double) : double                                             ┃
//    ┃ + seconds() : double                                                  ┃
//    ┃ + sign(x:double) : double                                             ┃
//    ┃ + sign(x:float) : float                                               ┃
//    ┃ + sin(x:double) : double                                              ┃
//    ┃ + sinh(x:double) : double                                             ┃
//    ┃ + sqrt(X:Complex) : Complex                                           ┃
//    ┃ + sqrt(x:double) : double                                             ┃
//    ┃ + stdDev_population() : double                                        ┃
//    ┃ + stdDev_population() : double                                        ┃
//    ┃ + stdDev_population_ofMean() : double                                 ┃
//    ┃ + stdDev_population_ofMean() : double                                 ┃
//    ┃ + StringDialog() : String                                             ┃
//    ┃ + StringDialog(message:String) : String                               ┃
//    ┃ + StringFromClipboard() : String                                      ┃
//    ┃ + StringScan() : String                                               ┃
//    ┃ + StringToClipboard(myStringToCopy:Object) : void                     ┃
//    ┃ + sub(a:double, b:double, x:double, y:double) : Complex               ┃
//    ┃ + sub(X:Complex, Y:Complex) : Complex                                 ┃
//    ┃ + sum() : double                                                      ┃
//    ┃ + sum() : int                                                         ┃
//    ┃ + tan(x:double) : double                                              ┃
//    ┃ + tanh(x:double) : double                                             ┃
//    ┃ + WriteFile(FilePathName:String, Contentsthrows:String) : void        ┃
//    ┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛
//endregion
    @SuppressWarnings({"WeakerAccess","unused","Duplicates","SuspiciousNameCombination"})
    public interface stringToString
    {
        String f(String x);
    }
    public static double log(double b,double x)
    {
        return Math.log(x)/Math.log(b);
    }
    public static void modifyFileText(File file,stringToString mod)
    {
        try
        {
            String text=readFile(file);
            text=mod.f(text);
            try
            {
                WriteFile(file.getAbsolutePath(),text);
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }
        catch(FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }
    public static File stringToFile(String x)
    {
        if(x.startsWith("file:"))
        {
            x=x.substring("file:".length());
        }
        return new File(x);
    }
    // public static File copyPasteDirectory(File source,File dest)
    // {
    //     import org.apache.commons.io.FileUtils;
// import org.json.JSONException;
// import org.json.JSONObject;
//
//         try
//         {
//             FileUtils.copyDirectory(source,dest);
//             return dest;
//         }
//         catch(IOException e)
//         {
//             e.printStackTrace();
//             return null;
//         }
//     }
    // public static File copyPasteFile(File sourceFile,File destDirectory)
    // {
    //     import org.apache.commons.io.FileUtils;
// import org.json.JSONException;
// import org.json.JSONObject;
//
//         try
//
//         {
//             System.out.println("COPYPASTA");
//             System.out.println(sourceFile);
//             System.out.println(destDirectory);
//             FileUtils.copyFileToDirectory(sourceFile,destDirectory);
//             return destDirectory;
//         }
//         catch(IOException e)
//         {
//             e.printStackTrace();
//             return null;
//         }
//     }
    public static String[] listAllPathsInDirectory(String directoryPath)
    {
        File[] files=listAllFilesInDirectory(directoryPath);
        String[] sheets=new String[0];
        if(files!=null)
        {
            sheets=new String[files.length];
            int i=0;
            for(File file : files)
            {
                sheets[i++]=file.getName();
            }
        }
        return sheets;
    }
    public static File[] listAllFilesInDirectory(String directoryPath)
    {
        if(directoryPath.startsWith("file:"))
        {
            directoryPath=directoryPath.substring("file:".length());
        }
        return new File(directoryPath).listFiles();
    }
    //region Squelchers
    public interface RunnableThatThrowsException
    {
        void run() throws Exception;
    }
    public static Exception tryCatchToException(RunnableThatThrowsException f)//Returns null if there is no exception
    {
        try
        {
            f.run();
            return null;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return e;
        }
    }
    public static boolean tryCatchToBoolean(RunnableThatThrowsException f)//True if succeeds false if fails
    {
        return tryCatchToBoolean(f,true);
    }
    public static boolean tryCatchToBoolean(RunnableThatThrowsException f,boolean printStackTrace)//True if succeeds false if fails
    {
        @SuppressWarnings("ThrowableResultOfMethodCallIgnored") Exception exception=tryCatchToException(f);
        if(exception==null)
        {
            return true;
        }
        exception.printStackTrace();
        return false;
    }
    public static void squelch(RunnableThatThrowsException f)
    {
        tryCatchToBoolean(f);
    }
    public static Runnable squelched(RunnableThatThrowsException f)
    {
        return ()->squelch(f);
    }
    //endregion
    public static Runnable seq(Runnable... runnables)//Concatenates the runnables
    {
        return ()->
        {
            for(Runnable r : runnables)
            {
                r.run();
            }
        };
    }
    public static void playSound(String FileName)
    {
        try
        {
            sun.audio.AudioPlayer.player.start(new sun.audio.AudioStream(new java.io.FileInputStream(FileName)));
        }
        catch(Exception ignored)
        {
            ignored.printStackTrace();
        }
    }
    public static String printed(String p)//For debugging
    {
        println(fansi(p,fansi_colors.cyan));
        return p;
    }
    public static String colorToHexString(Color c)
    {
        return "#"+(""+c.toString()).substring(2,8);
    }
    public static String rgbToHexString(int r,int g,int b)
    {
        return String.format("#%02x%02x%02x",r,g,b);//http://stackoverflow.com/questions/3607858/convert-a-rgb-color-value-to-a-hexadecimal
    }
    public static int[] hexToRGB(String hexWithoutHashtag)
    {
        return new int[]{Integer.valueOf(hexWithoutHashtag.substring(1-1,3-1),16),
                         Integer.valueOf(hexWithoutHashtag.substring(3-1,5-1),16),
                         Integer.valueOf(hexWithoutHashtag.substring(5-1,7-1),16)};
    }
    public static String mapString(String key,String[] keys,String[] values)//rCode: mapString A [A B C] [1 2 3] ⟹ 1
    {
        //Treats keys and values as if they were both halves of a dictionary
        String typeKey=key;//If this is not changed then we have an invalid key. To avoid errors we'll just let it go ahead anyway.
        assert keys.length==values.length;//If this fails we have a bad XML file
        int i=0;
        for(String s : keys)
        {
            if(s.equals(key))
            {
                return values[i];
            }
            i++;
        }
        return typeKey+"_invalidKey";
    }
    //region Alerts:error and info
    //Unfortunately the ones in the r class can't work on a javaFx thread. So, I'm going to implement some here:
    //All came from http://code.makery.ch/blog/javafx-dialogs-official/
    private static void fxShowAlert(String title,String header,String content,Alert.AlertType type)
    {
        Alert alert=new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
    public static void fxShowErrorAlert(String title,String header,String content)
    {
        fxShowAlert(title,header,content,Alert.AlertType.ERROR);
    }
    public static void fxShowErrorAlert(String message)
    {
        fxShowErrorAlert("Error",message,"");
    }
    public static void fxShowInfoAlert(String title,String header,String content)
    {
        fxShowAlert(title,header,content,Alert.AlertType.INFORMATION);
    }
    public static void fxShowInfoAlert(String message)
    {
        fxShowInfoAlert("Info",message,"");
    }
    //endregion
    //region StringDialog
    public static String fxStringDialog(String title,String header,String message)//Returns null if user cancels
    {
        TextInputDialog dialog=new TextInputDialog();
        dialog.setTitle(title);
        dialog.setHeaderText(header);
        dialog.setContentText(message);
        Optional<String> result=dialog.showAndWait();
        if(result.isPresent())
        {
            return result.get();
        }
        return null;//User decided to cancel.
    }
    public static boolean fxYesNo(String title,String header,String message)
    {
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        ButtonType yes=new ButtonType("Yes");
        ButtonType no=new ButtonType("No");
        // ButtonType buttonTypeThree = new ButtonType("Three");
        // ButtonType buttonTypeCancel = new ButtonType("Cancel",ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(yes,no);//, buttonTypeThree, buttonTypeCancel);
        Optional<ButtonType> result=alert.showAndWait();
        if(!result.isPresent())
        {
            return fxYesNo(title,header,message);//Keep asking the user until they click yes or no. Clicking the 'x' is not an option.
        }
        if(result.get()==no)
        {
            return false;
        }
        assert result.get()==yes;
        return true;
    }
    public static boolean fxYesNo(String message)
    {
        return fxYesNo("",message,"");
    }
    public static File fxOpenFile(Stage stage,File currentDirectory)//File getCurrentDirectory() {return new File(".");}
    {
        return fxGetFileChooser(currentDirectory).showOpenDialog(stage);
    }
    public static File fxSaveFile(Stage stage,File currentDirectory)//File getCurrentDirectory() {return new File(".");}
    {
        return fxGetFileChooser(currentDirectory).showSaveDialog(stage);
    }
    public static File fxOpenDirectory(File currentDirectory,Stage stage)//File getCurrentDirectory() {return new File(".");}
    {
        DirectoryChooser directoryChooser=new DirectoryChooser();
        directoryChooser.setInitialDirectory(currentDirectory);
        return new DirectoryChooser().showDialog(stage);
    }
    public static FileChooser fxGetFileChooser(File currentDirectory)//File getCurrentDirectory() {return new File(".");}
    {
        FileChooser fileChooser=new FileChooser();
        fileChooser.setInitialDirectory(currentDirectory);
        return fileChooser;
    }
    public static String getParentDir(String dir)
    {
        //EXAMPLE: /Users/Ryan/Desktop/picture.png ⟹ /Users/Ryan/Desktop/
        //EXAMPLE: /Users/Ryan/Desktop/ ⟺ /Users/Ryan/Desktop/
        //EXAMPLE: /Users/Ryan/Desktop ⟹ /Users/Ryan/
        dir=reversed(dir);
        dir=dir.substring(dir.indexOf('/'));
        dir=reversed(dir);
        return dir;
    }
    public static void fxRunAsNewThreadTimer(double frequencyInHz,Runnable f)//Akin to a second-life timer, except it runs in javaFx as a new thread
    {
        Timeline timeline=new Timeline(new KeyFrame(Duration.seconds(1/frequencyInHz),x->f.run()));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
    //region Setters
    /*@formatter:off*/
    // public static void setImageViewLocatedImage(String pathOrUrl,ImageView imageView)//A helper method
    // {
    //     if(!((LocatedImage)imageView.getImage()).getURL().equals(pathOrUrl))
    //     {
    //         imageView.setImage(LocatedImage.fromPathOrUrl(pathOrUrl));
    //     }
    // }
    // public static String toRGBCode(Color color)
    // {
    //     return String.format("#%02X%02X%02X",(int)color.getRed()*255,(int)color.getGreen()*255,(int)color.getBlue()*255);
    // }
    //region Datepicker filter
    public interface dateToBoolean
    {
        boolean f(LocalDate x);
    }
    public static void applyDateFilter(DatePicker datePicker,dateToBoolean filter)//Derived from http://download.java.net/jdk8/jfxdocs/javafx/scene/control/DatePicker.html#dayCellFactoryProperty
    {
        final Callback<DatePicker,DateCell> dayCellFactory=new Callback<DatePicker,DateCell>()
        {
            public DateCell call(final DatePicker datePicker)
            {
                return new DateCell()
                {
                    @Override
                    public void updateItem(LocalDate item,boolean empty)
                    {
                        super.updateItem(item,empty);
                        // if(MonthDay.from(item).equals(MonthDay.of(9,25)))
                        // {
                        //     setTooltip(new Tooltip("Happy Birthday!"));
                        //     setStyle("-fx-background-color: #ff4444;");
                        // }
                        // if(item.equals(LocalDate.now().plusDays(1)))
                        // {
                        //     // Tomorrow is too soon.
                        //     setDisable(true);
                        //     // setMaxHeight(0);
                        //     // setMaxWidth(0);
                        // }
                        // if(!item.getDayOfWeek().equals(DayOfWeek.MONDAY))
                        // {
                        //     // Tomorrow is too soon.
                        //     setDisable(true);
                        //     // setMaxHeight(0);
                        //     // setMaxWidth(0);
                        // }
                        try
                        {
                            if(item!=null&&!empty)
                            {
                                setDisable(!filter.f(item));
                            }
                        }
                        catch(Exception ignored)
                        {
                            // ignored.printStackTrace();
                        }
                    }
                };
            }
        };
        datePicker.setDayCellFactory(dayCellFactory);
    }
    //endregion
    public static void enableTheGoodOldAssertionKeyword()
    {
        ClassLoader.getSystemClassLoader().setDefaultAssertionStatus(true);//Netbeans is stupid and has assertions turned off by default. This turns them on.
    }
    public static void disableTheGoodOldAssertionKeyword()
    {
        ClassLoader.getSystemClassLoader().setDefaultAssertionStatus(false);
    }
    public static void branch(Runnable r,boolean b)//Adopted from Unreal Engine Blueprints
    {
        if(b)
        {
            r.run();
        }
    }
    // public static String jsonToPrettyString(JSONObject x)//Has \n's in it, instead of jamming the whole thing into a single line (which is good for my parenthesis automator, but not good for anybody else)
    // {
    //     import org.apache.commons.io.FileUtils;
// import org.json.JSONException;
// import org.json.JSONObject;
//
//         try
//         {
//             Source: https://stackoverflow.com/questions/4105795/pretty-print-json-in-java/7310424
            // return x.toString(4);
        // }
        // catch(JSONException e)
        // {
        //     e.printStackTrace();
        //     noinspection NumericOverflow
            // return ""+1/0;//I know this will cause some sort of error in the future, and I don't really want to catch
        // }
    // }
    public static void setComboboxOption(ComboBox c,Object option)
    {
        /*@formatter:off*/
        if(option==null||option.toString().equals("null"))
            c.getSelectionModel().clearSelection();
        else
        //noinspection unchecked
            c.getSelectionModel().select(option);
        /*@formatter:on*/
    }
    public static void setDatePickerValue(DatePicker d,String s)
    {
        try
        {
            d.setValue(LocalDate.parse(s));
        }
        catch(Exception ignored)
        {
            d.setValue(null);
        }
    }
    public static String reversed(String forward)
    {
        return (new StringBuilder(forward)).reverse().toString();
    }
    // public static JSONObject readJson(String path)
    // {
    //     import org.apache.commons.io.FileUtils;
// import org.json.JSONException;
// import org.json.JSONObject;
//
//         try
//         {
//             return new JSONObject(ReadFileIgnoreExceptions(path));
//         }
//         catch(JSONException e)
//         {
//             say("Could not find json file see stack trace");
//             e.printStackTrace();
//             return null;
//         }
//     }
    public static void setComboboxOptions(ComboBox c,String... options)
    {
        c.getItems().clear();
        for(String s : options)
        //noinspection unchecked
        {
            c.getItems().add(s);
        }
    }
    public static String[] allInRangeInclusive(String first,String last,String[] list)
    {
        try
        {
            assert contains(first,list);
            assert contains(last,list);
            return subArray(list,Math.min(indexOf(first,list),indexOf(last,list)+1),Math.max(indexOf(first,list),indexOf(last,list)+1));
        }
        catch(Exception ignored)//I don't know what the error is but it doesnt seem to be important for the course-site-generator project. It happens, sometimes, when changing the start/end office hours.
        {
            // r.say("Helo me im scared help m e");
            return null;
        }
    }
    public static String[] allBeforeInclusive(String last,String[] list)
    {
        assert contains(last,list);
        return subArray(list,0,indexOf(last,list)+1);
    }
    public static String[] allAfterInclusive(String first,String[] list)
    {
        assert contains(first,list);
        return subArray(list,indexOf(first,list),list.length);
    }
    public static String[] subArray(String[] array,int firstIndex,int lastIndexPlusOne)
    {
        String[] out=new String[lastIndexPlusOne-firstIndex];
        System.arraycopy(array,firstIndex,out,0,out.length);
        return out;
    }
    public static int indexOf(String element,String[] list)
    {
        for(int i=0;i<list.length;i++)
        {
            if(list[i].equals(element))
            {
                return i;
            }
        }
        return 1/0;
    }
    public static boolean contains(String element,String[] list)
    {
        return r.tryCatchToBoolean(()->indexOf(element,list));
    }
    //-------------------------------------------------------------------
    public static boolean contains(int i,int[] a)//Returns true if a contains i
    {
        /*@formatter:off*/
        for(int x:a)
            if(x==i)
                return true;
        return false;
        /*@formatter:on*/
    }
    public interface rRunnable<T>//A runnable that accepts a single argument. rRunnable≣Ryan Runnable
    {
        void run(T x);
    }
    //region ―――――――――――――――――――――――――――――COURSE SITE GENERATOR SPECIFIC THINGS―――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――
    public static void fitToParent(Node x)//Used for boilerplates
    {
        VBox.setVgrow(x,Priority.ALWAYS);
        HBox.setHgrow(x,Priority.ALWAYS);
        AnchorPane.setLeftAnchor(x,0d);
        AnchorPane.setRightAnchor(x,0d);
        AnchorPane.setTopAnchor(x,0d);
        AnchorPane.setBottomAnchor(x,0d);
    }
    public static String id(int... rowCol)
    {
        assert rowCol.length==2;//Should consist of a row and col
        return rowCol[0]+"_"+rowCol[1];
    }
    public static int[] rowCol(String id)
    {
        /*@formatter:off*/
        int[]out=new int[2];
        assert id.split("_").length==2;//Should be in the form 12_4 or 2_6 or 452_234 etc
        int i=0;
        for(String s:id.split("_"))
            out[i++]=Integer.parseInt(s);
        return out;
        /*@formatter:on*/
    }
    //region Email checker ［isValidEmail］
    public static boolean isValidEmail(String s)
    {
        //CODE FROM: https://www.mkyong.com/regular-expressions/how-to-validate-email-address-with-regular-expression/
        //⁠⁠⁠⁠⁠              ⎧                                                                                          ⎫
        //⁠⁠⁠⁠⁠              ⎪                   ⎧                 ⎫               ⎧               ⎫ ⎧               ⎫  ⎪
        //⁠⁠⁠⁠⁠              ⎪  ⎧              ⎫ ⎪   ⎧           ⎫ ⎪  ⎧          ⎫ ⎪   ⎧         ⎫ ⎪ ⎪   ⎧      ⎫⎧  ⎫⎪  ⎪
        return s.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        //⁠⁠⁠⁠⁠              ⎪  ⎩              ⎭ ⎪   ⎩           ⎭ ⎪  ⎩          ⎭ ⎪   ⎩         ⎭ ⎪ ⎪   ⎩      ⎭⎩  ⎭⎪  ⎪
        //⁠⁠⁠⁠⁠              ⎪                   ⎩                 ⎭               ⎩               ⎭ ⎩               ⎭  ⎪
        //⁠⁠⁠⁠⁠              ⎩                                                                                          ⎭
    }
    //endregion
    //region String line manipulators ［splitLines，joinLines，addLine，toggleLine，removeMatchingLines，replaceMatchingLines，hasMatchingLine，numberOfMatchingLines，sortedLines］
    //These were all originally created to updateTANameEmail a TA name in TAManager office hours grid in the CSE219 Course Site Generator project
    public static String lineSeparator="\n";//Just in case I want to change this for whatever reason
    public static String[] splitLines(String body)//To decrease redundancy
    {
        //tested ⋀ verified ✔
        if(body.equals(""))//Don't return [""], which would happen without this check for some reason
        {
            return new String[0];
        }
        return body.split(lineSeparator);//⟵ Self explanatory
    }
    public static String joinLines(String... lines)
    {
        return joinLines((Object[])lines);
    }
    public static String joinLines(Object[] lines)//Objects are implicitly converted to strings
    {
        //tested ⋀ verified ✔ (tested edge cases: where lines.length ∈ {0,1,2,3})
        /*@formatter:off*/
        String out="";
        if(lines.length==0)
            return out;
        int i=0;//a counter variable used to determine when we use the last line. Don't worry about it.
        for(Object line:lines)
        {
            out+=line;
            if(++i!=lines.length)//If 'line' is not the last element of 'lines'
               out+=lineSeparator;
        }
        return out;
        /*@formatter:on*/
    }
    public static String addLine(String body,String line)
    {
        if(body.equals(""))
        {
            return line;
        }
        return body+lineSeparator+line;
    }
    public static String removeMatchingLines(String body,String line)
    {
        String[] lines=splitLines(body);
        String[] out=new String[lines.length-numberOfMatchingLines(body,line)];
        int i=0;
        for(String l : lines)
        {
            if(!l.equals(line))
            {
                out[i++]=l;
            }
        }
        return joinLines(out);
    }
    public static String toggleLine(String body,String line)
    {
        //Toggles the existence of 'line' in 'body'. If line is not in body,
        if(body.equals(""))
        {
            return line;
        }
        if(!hasMatchingLine(body,line))
        {
            return body+lineSeparator+line;
        }
        return removeMatchingLines(body,line);
    }
    public static String replaceMatchingLines(String body,String oldLine,String newLine)
    {
        //Replaces all splitLines in 'body' that are equal to 'oldLine' to 'newLine'.
        ArrayList<CharSequence> newLines=new ArrayList<>();
        for(String line : splitLines(body))
        {
            newLines.add(line.equals(oldLine)?newLine:line);
        }
        return joinLines(newLines.toArray());//StringUtils.join(newLines,lineSeparator);
    }
    public static boolean hasMatchingLine(String body,String line)//If body has has a line that matches 'line', it returns true. Else, false.
    {
        return numberOfMatchingLines(body,line)!=0;//Is always ∈ ℕ
    }
    public static int numberOfMatchingLines(String body,String line)
    {
        //Returns the number of splitLines in 'body' that are equal to 'line'
        int i=0;
        for(String x : splitLines(body))
        {
            if(x.equals(line))
            {
                i++;
            }
        }
        return i;
        /*@formatter:on*/
    }
    public static String sortedLines(String body)
    {
        //tested ⋀ verified ✔
        //Sorts the splitLines of 'body' in alphabetical order.
        String[] x=splitLines(body);
        Arrays.sort(x);
        return joinLines(x);//String.join(lineSeparator,(CharSequence[])x);
    }
    //endregion
    //endregion
    //
    //region Console Outputs: print() and println() methods:
    public static void println()
    {
        System.out.print("\n");
    }
    public static void println(String message)
    {
        System.out.println(message);
    }
    public static void print(String message)
    {
        System.out.print(message);
    }
    public static void print(char message)
    {
        System.out.print(message);
    }
    public static void println(char message)
    {
        System.out.println(message);
    }
    public static void println(long message)
    {
        System.out.println(""+message);
    }
    public static void print(long message)
    {
        System.out.print(""+message);
    }
    public static void println(double message)
    {
        System.out.println(""+message);
    }
    public static void print(double message)
    {
        System.out.print(""+message);
    }
    public static void println(boolean message)
    {
        System.out.println(""+message);
    }
    public static void print(boolean message)
    {
        System.out.print(""+message);
    }
    //Special: Can be expanded as I learn new classes
    public static void println(BigDecimal message)
    {
        System.out.println(message.toPlainString());
    }
    public static void print(BigDecimal message)
    {
        System.out.print(message.toPlainString());
    }
    public static void print(Object... message)
    {
        print(Arrays.toString(message));
    }
    public static void println(Object... message)
    {
        print(message);
        println();
    }
    //END FOLD
    //endregion
    //region Console Inputs: {int,long,float,double,boolean,String}Scan() methods:
    public static int intScan()
    {
        return new Scanner(System.in).nextInt();
    }
    public static long longScan()
    {
        return new Scanner(System.in).nextLong();
    }
    public static float floatScan()
    {
        return new Scanner(System.in).nextFloat();
    }
    public static double doubleScan()
    {
        return new Scanner(System.in).nextDouble();
    }
    public static boolean booleanScan()
    {
        return new Scanner(System.in).nextBoolean();
    }
    public static String StringScan()
    {
        return new Scanner(System.in).nextLine();
    }
    public static String scan()
    {
        return new Scanner(System.in).nextLine();
    }
    public static String scan(String Message)
    {
        print(Message);
        return new Scanner(System.in).nextLine();
    }
    public static String StringDialog(String message)
    {
        return javax.swing.JOptionPane.showInputDialog(message);
    }
    public static String StringDialog()
    {
        return StringDialog("");
    }
    //endregion END FOLD
    //region Timing Methods: millis(), seconds(), delay(time), tic(),toc(),toc:
    final static long initMillis=System.currentTimeMillis();
    public static long millis()
    {
        return System.currentTimeMillis()-initMillis;
    }
    public static double seconds()
    {
        return millis()/1000d;
    }
    public static void delayInMillis(long delayDurationInMilliseconds)
    {
        //region Old version that always kills my laptop's battery!
//        long startTime=System.currentTimeMillis();
//        //noinspection StatementWithEmptyBody
//        while(System.currentTimeMillis()-delayDurationInMilliseconds-startTime<0);
        //endregion
        try
        {
            Thread.sleep(delayDurationInMilliseconds);
        }
        catch(Exception ignored)
        {
            ignored.printStackTrace();
        }
    }
    public static void delay(double delayDurationInSeconds)
    {
        //region Old version that always kills my laptop's battery!
//        long startTime=System.currentTimeMillis();
//        //noinspection StatementWithEmptyBody
//        while(System.currentTimeMillis()-delayDurationInSeconds*1000-startTime<0);
        //endregion
        try
        {
            Thread.sleep((long)(delayDurationInSeconds*1000));
        }
        catch(Exception ignored)
        {
            ignored.printStackTrace();
        }
    }
    //region Tic and Toc
    private static double toc_start=seconds();
    public static void tic()
    {
        toc_start=seconds();
    }
    public static double toc()
    {
        return seconds()-toc_start;
    }
    public static double ptoc()
    {
        double out=toc();
        System.out.println("ryansappframework.r.toc() ﹦ "+out);
        return out;
    }
    //endregion
    //END FOLD
    //endregion
    //region Array Math:
    public static int[] range(int Start_Inclusive,int Finish_Exclusive)
    {
        //Works for both forward-sequences, like 12345, and reversed-sequences like 98765.
        //Also works for negative numbers like -1,-2,-3 etc (in both directions of course)
        reversed(new Integer[]{1,2});
        //Generates an integer range like in Python or Matlab
        if(Start_Inclusive<Finish_Exclusive)
        {
            return java.util.stream.IntStream.range(Start_Inclusive,Finish_Exclusive).toArray();
        }
        else if(Start_Inclusive>Finish_Exclusive)
        {
            return toint(reversed(toInteger(range(Finish_Exclusive+1,Start_Inclusive+1))));
        }
        // ∴ else ⋂ Start_Inclusive == Finish_Exclusive ∴ (output is empty int[] array)
        return new int[0];
    }
    public static int[] rangeClosed(int Start_Inclusive,int Finish_Exclusive)
    {
        //Works for both forward-sequences, like 12345, and reversed-sequences like 98765.
        //Also works for negative numbers like -1,-2,-3 etc (in both directions of course)
        reversed(new Integer[]{1,2});
        //Generates an integer range like in Python or Matlab
        if(Start_Inclusive<Finish_Exclusive)
        {
            return java.util.stream.IntStream.rangeClosed(Start_Inclusive,Finish_Exclusive).toArray();
        }
        else if(Start_Inclusive>Finish_Exclusive)
        {
            return toint(reversed(toInteger(rangeClosed(Finish_Exclusive,Start_Inclusive))));
        }
        // ∴ else ⋂ Start_Inclusive == Finish_Exclusive ∴ (output is empty int[] array)
        return new int[]{Start_Inclusive};//====return new int[]{Finish_Inclusive};
    }
    public static Integer[] toInteger(int[] x)
    {
        //Converts an int[] array to an Integer[] array
        Integer[] out=new Integer[x.length];
        int i=0;
        for(int y : x)
        {
            out[i++]=y;
        }
        return out;
    }
    public static int[] toint(Integer[] x)
    {
        //Converts an Integer[] array to an int[] array
        int[] out=new int[x.length];
        int i=0;
        for(Integer y : x)
        {
            out[i++]=y;
        }
        return out;
    }
    public static <T> T[] reversed(T[] array)
    {
        //Using generics to reversed an array without the need for casting
        T[] copy=array.clone();
        Collections.reverse(Arrays.asList(copy));
        return copy;
    }
    public static byte[] reversed(byte[] array)//Built for speed over clarity
    {
        array=array.clone();
        byte temp;
        int i=0, j=array.length;
        while(i<j)
        {
            temp=array[i];
            array[i++]=array[--j];
            array[j]=temp;
        }
        return array;
    }
    public static byte[] roll(int n,byte[] array)
    {
        byte[] out=new byte[array.length];
        for(int i=0;i<array.length;i++)
        {
            out[i]=array[mod(i+n,array.length)];
        }
        return out;
    }
    public static double[] doubleArray(int... x)
    {
        double[] out=new double[x.length];
        for(int i=0;i<x.length;i++)
        {
            out[i]=x[i];
        }
        return out;
    }
    @SafeVarargs
    public static <T> T randomElement(T... array)
    {
        return array[randomIndex(array.length)];
    }
    //endregion
    //region All Real-Number Double Mathematics:
    //Math Constants:
    public static final double PI=3.1415926535897932384626433832795028841971693993751058209749;
    public static final double E=2.7182818284590452353602875;
    public static final double TWO_PI=2*PI;
    public static final double RAD_TO_DEG=360/TWO_PI;
    public static final double DEG_TO_RAD=TWO_PI/360;
    public static final double RAD_PER_DEG=RAD_TO_DEG;//<- Reworded just for clarification
    public static final double DEG_PER_RAD=DEG_TO_RAD;//<- Reworded just for clarification
    //END FOLD
    //Standard Misc Math:
    public static double sum(double... x)
    {
        double out=0;
        for(double y : x)
        {
            out+=y;
        }
        return out;
    }
    public static int sum(int... x)
    {
        int out=0;
        for(int y : x)
        {
            out+=y;
        }
        return out;
    }
    public static double avg(double... x)
    {
        return sum(x)/x.length;
    }
    public static double avg(int... x)
    {
        return avg(doubleArray(x));
    }
    public static double stdDev_population(double... x)
    {
        double avg=avg(x);
        for(int i=0;i<x.length;i++)
        {
            x[i]-=avg;
            x[i]*=x[i];
        }
        return sqrt(sum(x)/(x.length));
    }
    public static double stdDev_population(int... x)
    {
        return stdDev_population(doubleArray(x));
    }
    public static double stdDev_population_ofMean(double... x)
    {
        return stdDev_population(x)/sqrt(x.length);
    }
    public static double stdDev_population_ofMean(int... x)
    {
        return stdDev_population(x)/sqrt(x.length);
    }
    public static double sign(double x)
    {
        return java.lang.Math.signum(x);
    }
    public static float sign(float x)
    {
        return java.lang.Math.signum(x);
    }
    public static double abs(double x)
    {
        return java.lang.Math.abs(x);
    }
    public static double max(double x,double y)
    {
        return java.lang.Math.max(x,y);
    }
    public static int max(int x,int y)
    {
        return java.lang.Math.max(x,y);
    }
    public static double min(double x,double y)
    {
        return java.lang.Math.min(x,y);
    }
    public static long round(double x)
    {
        return java.lang.Math.round(x);
    }
    public static long ceil(double x)
    {
        return (long)java.lang.Math.ceil(x);
    }
    public static long floor(double x)
    {
        return (long)java.lang.Math.floor(x);
    }
    public static double mag(double x,double y)
    {
        return java.lang.Math.hypot(x,y);
    }
    public static double sqrt(double x)
    {
        return java.lang.Math.sqrt(x);
    }
    public static double random(double x)
    {
        return java.lang.Math.random()*x;
    }
    public static int randomInt(double x)
    {
        return (int)(java.lang.Math.random()*x);
    }
    public static int randomIndex(int length)
    {
        return (int)floor(random(length));
    }
    public static boolean chance(double Probability)
    {
        //0<Probability<1
        return random(1)<Probability;
    }
    public static long factorial(long x)
    {
        if(x<0)
        {
            //Cannot take the factorial of a negative integer! Exit the script and return an error message on the console...
            println("ERROR: long factorial(long x) : x="+x+" and x<0 : Cannot take the factorial of a negative integer!");
            System.exit(0);
        }
        int y=1;
        for(int i=1;i<=x;i++)
        {
            y*=i;
        }
        return y;
    }
    //END FOLD
    //Specialized Math Methods:
    public static double angleOf(double x,double y)
    {
        //double AngleOf: Returns the angle between the x-axis and a 2D coordinate in radians. Accepts any coordinate pair except (0,0).
        //Note: This function can be optimized by using arctan() instead of arcsin() and arccos(). I'm too lazy to bother with that today.
        double Magnitude=sqrt(x*x+y*y);
        x/=Magnitude;
        y/=Magnitude;
        if(y>0)
        {
            return acos(x);
        }
        else if(y<0)
        {
            return TWO_PI-acos(x);
        }
        else //if(y==0) <-- Implied
        {
            if(x>0)
            {
                return 0;
            }
            else if(x<0)
            {
                return PI;
            }
            else //if(x==0)
            {
                //Note: The name of this function is mentioned in the following error readout, so if "AngleOf" changes to something else change the error message too!
                println("Error: the point (0,0) does not have an angle! Returning 0... [double AngleOf(float x,float y)]");
                return 0;
            }
        }
    }
    public static double distance(double X,double Y,double x,double y)
    {
        X-=x;
        Y-=y;
        return sqrt(X*X+Y*Y);
    }
    //END FOLD
    //double exp(x) and ln(x):
    public static double exp(double x)
    {
        return java.lang.Math.exp(x);
    }
    public static double ln(double x)
    {
        return java.lang.Math.log(x);
    }
    //END FOLD
    //double Trig Functions:
    public static double sin(double x)
    {
        return java.lang.Math.sin(x);
    }
    public static double cos(double x)
    {
        return java.lang.Math.cos(x);
    }
    public static double tan(double x)
    {
        return java.lang.Math.tan(x);
    }
    public static double asin(double x)
    {
        return java.lang.Math.asin(x);
    }
    public static double acos(double x)
    {
        return java.lang.Math.acos(x);
    }
    public static double atan(double x)
    {
        return java.lang.Math.atan(x);
    }
    //Trig Reciprocals:
    public static double csc(double x)
    {
        return 1.0d/java.lang.Math.sin(x);
    }
    public static double sec(double x)
    {
        return 1.0d/java.lang.Math.cos(x);
    }
    public static double cot(double x)
    {
        return 1.0d/java.lang.Math.tan(x);
    }
    public static double acsc(double x)
    {
        return java.lang.Math.asin(1.0d/x);
    }
    public static double asec(double x)
    {
        return java.lang.Math.acos(1.0d/x);
    }
    public static double acot(double x)
    {
        return java.lang.Math.atan(1.0d/x);
    }
    //END FOLD
    //double Hyperbolic Trig Functions:
    public static double sinh(double x)
    {
        return java.lang.Math.sinh(x);
    }
    public static double cosh(double x)
    {
        return java.lang.Math.cosh(x);
    }
    public static double tanh(double x)
    {
        return java.lang.Math.tanh(x);
    }
    public static double asinh(double x)
    {
        return ln(x+sqrt(x*x+1));
    }
    public static double acosh(double x)
    {
        return ln(x+sqrt(x*x-1));
    }
    public static double atanh(double x)
    {
        return .5d*ln((1+x)/(1-x));
    }
    //Reciprocal Hyperbolic Trig Functions:
    public static double csch(double x)
    {
        return 1.0d/java.lang.Math.sinh(x);
    }
    public static double sech(double x)
    {
        return 1.0d/java.lang.Math.cosh(x);
    }
    public static double coth(double x)
    {
        return 1.0d/java.lang.Math.tanh(x);
    }
    public static double acsch(double x)
    {
        return asinh(1.0d/x);
    }
    public static double asech(double x)
    {
        return acosh(1.0d/x);
    }
    public static double acoth(double x)
    {
        return atanh(1.0d/x);
    }
    //END FOLD
    //endregion
    //region String Conversions:
    public static double Double(String Input)
    {
        return (new BigDecimal(Input)).doubleValue();
    }
    public static double Long(String Input)
    {
        return (new BigDecimal(Input)).longValue();
    }
    public static double Int(String Input)
    {
        return (new BigDecimal(Input)).intValue();
    }
    //endregionß
    //region Ryan's Complex Double Math Methods:
    public class Complex
    {
        public double real, imag;
        Complex(double RealIn,double ImaginaryIn)
        {
            real=RealIn;
            imag=ImaginaryIn;
        }
        double re()
        {
            return real;
        }
        double real()
        {
            return real;
        }
        double im()
        {
            return imag;
        }
        double imag()
        {
            return imag;
        }
        double abs()
        {
            return imag*imag+real*real;
        }
        double arg()
        {
            return r.angleOf(real,imag);
        }
        //Non-Math Methods:
        void PrintLn()
        {
            r.print(real);
            if(imag>=0)
            {
                r.print(" + ");
                r.print(imag);
            }
            else
            {
                r.print(" - ");
                r.print(""+(-imag));
            }
            r.println("i");
        }
    }
    double re(Complex x)
    {
        return x.re();
    }
    double real(Complex x)
    {
        return x.re();
    }
    double im(Complex x)
    {
        return x.im();
    }
    double imag(Complex x)
    {
        return x.im();
    }
    double abs(Complex x)
    {
        return x.abs();
    }
    double arg(Complex x)
    {
        return x.arg();
    }
    Complex complex(double real,double imag)//The lower-case "complex" is so we can write equations in single lines, rather than having to declare "blah.... = new Complex()" each time we want to perform a complex arithmetic operation.
    {
        return new Complex(real,imag);
    }
    Complex real(double real)//Returns a real number in the form of the Complex class, that has an imaginary component of zero and a real component of "real".
    {
        return new Complex(real,0);
    }
    Complex re(double real)//Returns a real number in the form of the Complex class, that has an imaginary component of zero and a real component of "real".
    {
        return new Complex(real,0);
    }
    Complex imag(double imag)//Returns a purely imaginary number in the form of the Complex class, that has an imaginary component of "imag" and a real component of 0.
    {
        return new Complex(0,imag);
    }
    Complex im(double imag)//Returns a purely imaginary number in the form of the Complex class, that has an imaginary component of "imag" and a real component of 0.
    {
        return new Complex(0,imag);
    }
    //Bivariate Complex Functions:
    Complex mult(Complex X,Complex Y)
    {
        double a=X.re();
        double b=X.im();
        double x=Y.re();
        double y=Y.im();
        return new Complex(a*x-b*y,a*y+b*x);
    }
    Complex mult(double a,double b,double x,double y)
    {
        //Returns a Complex number, (a+bi)*(x+yi), where a, b, x and y are all real numbers.
        return new Complex(a*x-b*y,a*y+b*x);
    }
    Complex div(Complex X,Complex Y)
    {
        double a=X.re();
        double b=X.im();
        double x=Y.re();
        double y=Y.im();
        double imagOut=(b*x-a*y)/(x*x+y*y);
        double realOut=(a*x+b*y)/(x*x+y*y);
        return new Complex(realOut,imagOut);
    }
    Complex div(double a,double b,double x,double y)
    {
        //Returns a Complex number, (a+bi)/(x+yi), where a, b, x and y are all real numbers.
        double imagOut=(b*x-a*y)/(x*x+y*y);
        double realOut=(a*x+b*y)/(x*x+y*y);
        return new Complex(realOut,imagOut);
    }
    Complex add(Complex X,Complex Y)
    {
        double a=X.re();
        double b=X.im();
        double x=Y.re();
        double y=Y.im();
        return new Complex(a+x,b+y);
    }
    Complex add(double a,double b,double x,double y)
    {
        //Returns a Complex number, (a+bi)+(x+yi), where a, b, x and y are all real numbers.
        return new Complex(a+x,b+y);
    }
    Complex sub(Complex X,Complex Y)
    {
        double a=X.re();
        double b=X.im();
        double x=Y.re();
        double y=Y.im();
        return new Complex(a-x,b-y);
    }
    Complex sub(double a,double b,double x,double y)
    {
        //Returns a Complex number, (a+bi)-(x+yi), where a, b, x and y are all real numbers.
        return new Complex(a-x,b-y);
    }
    //Univariate Complex Functions:
    Complex exp(Complex X)
    {
        double a=X.re();
        double b=X.im();
        double realOut=exp(a)*cos(b);
        double imagOut=exp(a)*sin(b);
        return new Complex(realOut,imagOut);
    }
    Complex exp(double a,double b)
    {
        //Returns a Complex number, exp(a+bi), where a and b are both real numbers.
        double realOut=exp(a)*cos(b);
        double imagOut=exp(a)*sin(b);
        return new Complex(realOut,imagOut);
    }
    Complex ln(Complex X)
    {
        double a=X.re();
        double b=X.im();
        double realOut=.5*ln(a*a+b*b);
        double imagOut=X.arg();
        return new Complex(realOut,imagOut);
    }
    Complex ln(double a,double b)
    {
        //Returns a Complex number, ln(a+bi), where a and b are both real numbers.
        double realOut=.5*ln(a*a+b*b);
        double imagOut=angleOf(a,b);
        return new Complex(realOut,imagOut);
    }
    //Higher-order complex functions...
    Complex pow(Complex X,Complex Y)
    {
        //Returns a Complex number, X^Y, where X and Y can both be complex numbers.
        return exp(mult(Y,ln(X)));
    }
    Complex pow(double a,double b,double x,double y)
    {
        //Returns a Complex number, (a+bi)^(x+yi), where a, b, x and y are all real numbers.
        return exp(mult(complex(x,y),ln(complex(a,b))));
    }
    Complex log(Complex Base,Complex X)//!!!Warning: Syntax order is arbitrary!!! Pay attention: It's log_(Base) of X.
    {
        //Returns a Complex number, the log of X in base Base, where X and Y can both be complex numbers.
        return div(ln(X),ln(Base));
    }
    Complex log(double Base_a,double Base_b,double X_x,double X_y)//!!!Warning: Syntax order is arbitrary!!! Pay attention: It's log_(Base) of X.
    {
        //Returns a Complex number, the log of X in base Base, where X and Y can both be complex numbers. X and Y are constructed from the four input-doubles.
        return log(complex(Base_a,Base_b),complex(X_x,X_y));
    }
    Complex sqrt(Complex X)
    {
        //Returns a Complex number, sqrt(X), where X is a Complex number.
        return pow(X,re(.5));
    }
    boolean Boolean(int x)
    {
        return x>=1;
    }
    int Int(boolean x)
    {
        if(x)
        {
            return 1;
        }
        return 0;
    }
    //END FOLD
    //endregion
    //region Mouse Information:
    public static int mouseX()
    {
        return MouseInfo.getPointerInfo().getLocation().x;
    }
    public static int mouseY()
    {
        return MouseInfo.getPointerInfo().getLocation().y;
    }
    //END FOLD
    //endregion
    //region Clipboard:
    public static void StringToClipboard(Object myStringToCopy)
    {
        java.awt.Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new java.awt.datatransfer.StringSelection(myStringToCopy.toString()),null);
    }
    public static String StringFromClipboard()
    {
        try
        {
            return (String)java.awt.Toolkit.getDefaultToolkit().getSystemClipboard().getData(java.awt.datatransfer.DataFlavor.stringFlavor);
        }
        catch(Exception a)
        {
            System.out.println("I honestly don't give a crap about catching this exception but it makes me put one here anyway.");
            return "ERROR4622888886666666666";
        }
    }
    //endregion
    //region Web:
    public static void OpenWebsite(String URL)
    {
        try
        {
            java.awt.Desktop.getDesktop().browse(new java.net.URI(URL));
        }
        catch(Exception x)
        {
            println("ERROR: Exception thrown: ryansappframework.r.OpenWebsite("+URL+"); : "+x.getMessage()+" : "+x.toString());
        }
    }
    //endregion
    //region Read/Write from/to Text Files:
    public static String ReadFile(String FilePathName) throws java.io.FileNotFoundException
    {
        File source=new File(FilePathName);
        return readFile(source);
    }
    public static String readFile(File source) throws FileNotFoundException
    {
        Scanner Input=new Scanner(source);
        if(!Input.hasNextLine())
        {
            return "";
        }
        String Output=Input.nextLine();
        while(Input.hasNextLine())
        {
            Output+="\n"+Input.nextLine();
        }
        Input.close();
        return Output;
    }
    public static String ReadFile(File f)
    {
        Scanner Input=null;
        try
        {
            Input=new Scanner(f);
        }
        catch(FileNotFoundException e)
        {
            e.printStackTrace();
        }
        if(!Input.hasNextLine())
        {
            return "";
        }
        String Output=Input.nextLine();
        while(Input.hasNextLine())
        {
            Output+="\n"+Input.nextLine();
        }
        Input.close();
        return Output;
    }
    public static void WriteFile(String FilePathName,String Contents) throws java.io.IOException
    {
        java.io.PrintWriter Writer=new java.io.PrintWriter(FilePathName);
        Writer.print(Contents);
        Writer.close();
    }
    public static String ReadFileIgnoreExceptions(String FilePathName)
    {
        try
        {
            ReadFile(FilePathName);
        }
        catch(Exception ignored)
        {
        }
        return null;
    }
    public static void WriteFileIgnoreExceptions(String FilePathName,String Contents)
    {
        try
        {
            WriteFile(FilePathName,Contents);
        }
        catch(Exception ignored)//I want it to crash my code but i dont want to catch the exception.
        {
            r.say("Write file failed");
            ignored.printStackTrace();
            int g=1/0;
        }
    }
    //endregion
    //region Screen Width/Height (In Pixels) (AKA The resolution):
    public static final int screenWidth=(int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    public static final int screenHeight=(int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
    public static int ScreenWidth()
    {
        //NOTE: In order to get the maximum pixel in a dimension, you must subtract 1 from this!
        //(When trying to detect if mouse is in corner of screen for example)
        return screenWidth;
    }
    public static int ScreenHeight()
    {
        //NOTE: In order to get the maximum pixel in a dimension, you must subtract 1 from this!
        //(When trying to detect if mouse is in corner of screen for example)
        return screenHeight;
    }
    //endregion
    //region Audio: Text to Speech (only on Macs)
    public enum voices
    {
        //Used for the 'say' method. All possible voices on the MacOsx say command.
        Samantha,Bad,Bahh,Bells,Boing,Bubbles,Cellos,Deranged,Good,Hysterical,Pipe,Trinoids,Whisper,Zarvox,Agnes,Kathy,Princess,Vicki,Victoria,Alex,Bruce,Fred,Junior,Ralph,Albert
    }
    public static void say(String message,voices voice)
    {
        //I've only tested this method on my Mac. I don't think it will work on windows as it relies on the Mac's terminal-based courseInfo_subjectText-to-speech.
        //It will invoke a courseInfo_subjectText-to-speech method on the Mac's bash (or terminal or shell idk what to call it).
        if(toc()>.25)
        {
            attemptShellCommandIgnoreExceptions("say -v "+voice+" "+message);
            tic();
        }
    }
    public static void say(String message)
    {
        voices defaultVoice=voices.Samantha;//This can be changed based on how I feel today.
        say(message,defaultVoice);
    }
    //endregion
    //region fansi (Colorful console courseInfo_subjectText via ANSI stuff)
    public enum fansi_colors
    {
        black(0),red(1),green(2),yellow(3),blue(4),magenta(5),cyan(6),gray(7);
        private final int id;
        fansi_colors(int id)
        {
            this.id=id;
        }
        public int getValue()
        {
            return id;
        }
    }
    public enum fansi_styles
    {
        bold(1),faded(2),underlined(4),blinking(5),outlined(7);
        private final int id;
        fansi_styles(int id)
        {
            this.id=id;
        }
        public int getValue()
        {
            return id;
        }
    }
    public static String fansi(String s,r.fansi_colors textColor,r.fansi_styles textStyle,r.fansi_colors background_color)
    {
        return "\033["+textStyle.getValue()+";"+(30+textColor.getValue())+";"+(40+background_color.getValue())+"m"+s+"\033[0m";//The order of textStyle.getValue(),textColor.getValue(),background_color.getValue()
    }
    public static String fansi(String s,r.fansi_colors textColor,r.fansi_styles textStyle)
    {
        return "\033["+textStyle.getValue()+";"+(30+textColor.getValue())+"m"+s+"\033[0m";//The order of textStyle.getValue(),textColor.getValue(),background_color.getValue()
    }
    public static String fansi(String s,r.fansi_colors textColor)
    {
        return "\033["+(30+textColor.getValue())+"m"+s+"\033[0m";//The order of textStyle.getValue(),textColor.getValue(),background_color.getValue()
    }
    //endregion
    public static void attemptShellCommandIgnoreExceptions(String command)
    {
        try
        {
            Runtime.getRuntime().exec(command);
        }
        catch(Exception ignored)
        {
        }
    }
    public static String replaceAll(String Original,String OldSnippet,String NewSnippet)
    {
        return Original.replaceAll(Pattern.quote(OldSnippet),NewSnippet);
    }
    public static String removeAll(String Original,String Snippet)
    {
        return replaceAll(Original,Snippet,"");
    }
    public static Object eval(String javascriptCode)
    {
        try
        {
            return (new javax.script.ScriptEngineManager()).getEngineByName("JavaScript").eval(javascriptCode);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
    // The following is only commented out because it makes more sense, in the CourseSiteGenerator project, to keep them in the Dialogs class. Once I'm done with this project these should be made static then uncommented.
    //region non-javaFx okCancel/Yes/No/Cancel Dialogs
    static public enum dialogOptions//Used for checking the values of various dialog results
    {
        YES,NO,CANCEL
    }
    static public dialogOptions yesNoCancel(String title,String message)
    {
        /*@formatter:off*/
        //Example: r.print(new App().gui.dialogs.yesNoCancel("title","Message")==Dialogs.dialogOptions.CANCEL);  //⟵ Returns true if user selects cancel else false
        //Based on: http://www.java2s.com/Tutorial/Java/0240__Swing/Yesnocanceldialog.htm
        int out=JOptionPane.showConfirmDialog(null,message,title,JOptionPane.YES_NO_CANCEL_OPTION);
        if(out==0)
            return dialogOptions.YES;
        if(out==1)
            return dialogOptions.NO;
        if(out==2)
            return dialogOptions.CANCEL;
        assert false;//This should never happen.
        return null;
        /*@formatter:on*/
    }
    static public boolean yesNo(String title,String message)
    {
        //Example: r.print(new App().gui.dialogs.yesNo("title","Message")==Dialogs.dialogOptions.CANCEL);  //⟵ Returns true if user selects cancel else false
        //Based on: http://www.java2s.com/Tutorial/Java/0240__Swing/Yesnocanceldialog.htm
        return JOptionPane.showConfirmDialog(null,message,title,JOptionPane.YES_NO_OPTION)==0;//0⟺YES，1⟺NO
    }
    static public boolean okCancel(String title,String message)
    {
        return javax.swing.JOptionPane.showConfirmDialog(null,message,title,JOptionPane.OK_CANCEL_OPTION)==0;
    }
    //endregion―――――――――――――――――――――――――――――――――――――――――
    //region File/Dir Open/Save Dialogs
    //DOCUMENTATION:
    //'Saving' refers to a dialog where the user has to put in a string, as you would normally do when saving a file.
    //'Opening' refers to selecting exclusively from files/directories that are already there.
    //String[] extensions refers to the available types of files you normally choose from. Leave it blank to enable all filetypes.
    //All methods here are derived from four combinations of true and false from fileDialog, which is private for that reason.
    //These methods can be copy-pasted to and from the r class if necessary.
    static private File fileDialog(String title,boolean openIfTrueElseSave,boolean fileIfTrueElseDir,String... extensions)//Leave extensions blank to accept all file types
    {
        /*@formatter:off*/
        JFileChooser chooser=new JFileChooser();
        chooser.setDialogTitle(title);
        if(extensions.length!=0)chooser.setFileFilter(new FileNameExtensionFilter(Arrays.toString(extensions),extensions));
        chooser.setFileSelectionMode(fileIfTrueElseDir?JFileChooser.FILES_ONLY:JFileChooser.DIRECTORIES_ONLY);
        chooser.setCurrentDirectory(new java.io.File("."));//Set pwd
        if(openIfTrueElseSave)chooser.showOpenDialog(null);//Show the dialog
        else chooser.showSaveDialog(null);//Show the dialog
        return chooser.getSelectedFile();
        /*@formatter:on*/
    }
    static public File openFile(String title,String... extensions)
    {
        return fileDialog(title,true,true,extensions);
    }
    static public File openDirectory(String title,String... extensions)
    {
        return fileDialog(title,true,false,extensions);
    }
    static public File saveFile(String title,String... extensions)//Save is defined as a thing where you gotta type out a new file path or something in the dialog, as opposed to open where you only select whats there
    {
        return fileDialog(title,false,true,extensions);
    }
    static public File saveDirectory(String title,String... extensions)
    {
        return fileDialog(title,false,false,extensions);
    }
    //endregion
    public static final double mod(double a,double b)//Because java's stupid and modulo can return negative numbers
    {
        return a<0?b-(-a)%b:a%b;
    }
    public static final int mod(int a,int b)//Because java's stupid and modulo can return negative numbers
    {
        return a<0?b-(-a)%b:a%b;
    }
    public final static double π=3.14159265359;
    public final static double τ=2*π;
    public final static double linterp(double x,double... y)
    {
        assert (int)x<=y.length-1:"linterp error: x would exceed max index of y. x="+x+" and y.length="+y.length;
        assert x>=0:"linterp error: x cannot be negative (duh!). x="+x+" and y.length="+y.length;
        int cx=(int)r.ceil(x);
        int fx=(int)r.floor(x);
        return lerp(x,fx,cx,y[fx],y[cx]);
    }
    public final static double linterpCyclic(double x,double... y)
    {
        int cx=(int)r.ceil(x);
        int fx=(int)r.floor(x);
        return lerp(x,fx,cx,y[fx%y.length],y[cx%y.length]);
    }
    public final static double linterpCyclicCumulative(double x,double... y)//Verified
    {
        int l=y.length-1;
        long f=r.floor(x/l);
        return f*(y[l]-y[0])+linterpClamped(x-f*l,y);
    }
    public static double linterpClamped(double x,double... y)
    {
        return linterp(clamp(x,0,y.length-1),y);
    }
    public final static double clamp(double x,double min,double max)
    {
        // assert min<=max;
        if(min>max)
        {
            double temp=max;
            max=min;
            min=temp;
        }
        return x<min?min:x>max?max:x;
    }
    public final static double lerp(double x,double xmin,double xmax,double ymin,double ymax)
    {
        if(ymin==ymax)
        {
            return ymax;
        }
        if(xmin==xmax)
        {
            return (ymin+ymax)/2;
        }
        // if(x==xmin)
        //     return ymin;
        // if(x==xmax)
        //     return ymax;
        assert xmin<=xmax;
        assert ymin<=ymax;
        x-=xmin;//∴ x>=0 (If x was min before, now x is 0)
        x/=xmax-xmin;//Divide x by its range
        x*=ymax-ymin;//Multiply x by int's range
        x+=ymin;
        // System.out.println(x);
        return x;
    }
    public final static double blend(double x,double y,double α)
    {
        return x*(1-α)+y*α;
    }
    public final static double clampedLerp(double x,double xmin,double xmax,double ymin,double ymax)//Originally created for audio: ℝ ∈［-1，1］⟺ ℤ ∈ [﹣2³¹，2³¹﹣1］
    {
        assert xmin<=xmax;
        assert ymin<=ymax;
        return clamp(lerp(x,xmin,xmax,ymin,ymax),ymin,ymax);
    }
    public final static int doubleRangeToInt(double x,double xmin,double xmax)//Originally created for audio: ℝ ∈［-1，1］⟺ ℤ ∈ [﹣2³¹，2³¹﹣1］
    {
        assert xmin<=xmax;
        int imin=-2147483648, imax=2147483647;//Ints are 32-bit signed integers. 2³¹﹦2147483648
        return (int)clampedLerp(x,xmin,xmax,imin,imax);
    }
    public final static short doubleRangeToShort(double x,double xmin,double xmax)//Originally created for audio: ℝ ∈［-1，1］⟺ ℤ ∈ [﹣2¹⁵，2¹⁵﹣1］
    {
        assert xmin<=xmax;
        short smin=-32768, smax=32767;//Shorts are 16-bit signed integers. 2¹⁵﹦32768
        return (short)clampedLerp(x,xmin,xmax,smin,smax);
    }
    public final static byte doubleRangeToByte(double x,double xmin,double xmax)//Originally created for audio: ℝ ∈［-1，1］⟺ ℤ ∈ [﹣2⁷，2⁷﹣1］
    {
        assert xmin<=xmax;
        short smin=-128, smax=127;//Shorts are 8-bit signed integers. 2⁷﹦128
        return (byte)clampedLerp(x,xmin,xmax,smin,smax);
    }
    public final static int audioRangeToInt(double x)//x should ∈［﹣1，1］
    {
        return doubleRangeToInt(x,-1,1);
    }
    public final static short audioRangeToShort(double x)//x should ∈［﹣1，1］
    {
        return doubleRangeToShort(x,-1,1);
    }
    public final static byte audioRangeToByte(double x)//x should ∈［﹣1，1］
    {
        return doubleRangeToByte(x,-1,1);
    }
    @SuppressWarnings("PointlessBitwiseExpression")
    public final static byte[] doublesTo32BitAudioBytes(double[] monoAudio)//∀ x ∈ monoAudio，x should ∈［﹣1，1］or else it will be clipped
    {
        //For my Modulin Synth attempt in Java
        //https://stackoverflow.com/questions/1936857/convert-integer-into-byte-array-java
        ByteBuffer bytes=ByteBuffer.allocate(4*monoAudio.length);
        bytes.order(ByteOrder.BIG_ENDIAN); // optional, the initial order of a byte buffer is always BIG_ENDIAN.
        for(double sample : monoAudio)
        {
            bytes.putInt(audioRangeToInt(sample));
        }
        return bytes.array();
    }
    public final static byte[] doublesTo16BitAudioBytes(double[] monoAudio)//∀ x ∈ monoAudio，x should ∈［﹣1，1］or else it will be clipped
    {
        //For my Modulin Synth attempt in Java
        //https://stackoverflow.com/questions/1936857/convert-integer-into-byte-array-java
        ByteBuffer bytes=ByteBuffer.allocate(2*monoAudio.length);
        bytes.order(ByteOrder.BIG_ENDIAN); // optional, the initial order of a byte buffer is always BIG_ENDIAN.
        for(double sample : monoAudio)
        {
            bytes.putShort(audioRangeToShort(sample));
        }
        return bytes.array();
    }
    public final static byte[] doublesTo8BitAudioBytes(double[] monoAudio)//∀ x ∈ monoAudio，x should ∈［﹣1，1］or else it will be clipped
    {
        //For my Modulin Synth attempt in Java
        //https://stackoverflow.com/questions/1936857/convert-integer-into-byte-array-java
        ByteBuffer bytes=ByteBuffer.allocate(monoAudio.length);
        bytes.order(ByteOrder.BIG_ENDIAN); // optional, the initial order of a byte buffer is always BIG_ENDIAN.
        for(double sample : monoAudio)
        {
            bytes.put(audioRangeToByte(sample));
        }
        return bytes.array();
    }
    public static double[][] transposeMatrix(double[][] m)
    {
        double[][] temp=new double[m[0].length][m.length];
        for(int i=0;i<m.length;i++)
        {
            for(int j=0;j<m[0].length;j++)
            {
                temp[j][i]=m[i][j];
            }
        }
        return temp;
    }
    public static int[][] transposeMatrix(int[][] m)
    {
        int[][] temp=new int[m[0].length][m.length];
        for(int i=0;i<m.length;i++)
        {
            for(int j=0;j<m[0].length;j++)
            {
                temp[j][i]=m[i][j];
            }
        }
        return temp;
    }
    public static Thread startTimerThread(Runnable r,double timeIntervalInSeconds)
    {
        Thread thread=new Thread(()->
                                 {
                                     //noinspection InfiniteLoopStatement
                                     while(true)
                                     {
                                         r.run();
                                         delay(timeIntervalInSeconds);
                                     }
                                 });
        thread.start();
        return thread;
    }
    // public final static byte[]doublesTo32BitAudioBytes(double[]monoAudio)//∀ x ∈ monoAudio，x should ∈［﹣1，1］or else it will be clipped
    // {
    //     //For my Modulin Synth attempt in Java
    //     //https://stackoverflow.com/questions/36577070/playing-sound-through-sourcedataline-write-how-to-use-the-byte-buffer-with-d
    //     final byte[] bytes=new byte[monoAudio.length*4];
    //     int i=0;
    //     for(double dsample:monoAudio)
    //     {
    //         final int isample=audioRangeToInt(dsample)-Integer.MIN_VALUE;
    //         bytes[i++] = (byte)((isample >> 0)  & 0xff);
    //         bytes[i++] = (byte)((isample >> 8)  & 0xff);
    //         bytes[i++] = (byte)((isample >> 16) & 0xff);
    //         bytes[i++] = (byte)((isample >> 24) & 0xff);
    //     }
    //     return bytes;
    // }
}
//region The R Class
//endregion