package RecordMovie;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.Date;
import java.util.TimerTask;
import java.util.Calendar;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.imageio.ImageIO;

public class SampleTask2 extends TimerTask{
		private boolean judge = false;
	    private String TtimeStamp;
	    private String folder_name;
	    private long dayDiff;
	    private File file = null;
	    private FileWriter pw = null;
	    private boolean first = false;

		public SampleTask2(String folder_name) {
			this.folder_name = folder_name;
		}


		@Override
		public void run() {
			if(judge){
				Robot robot = null;
				try {
					robot = new Robot();
				} catch (AWTException e) {

					e.printStackTrace();
				}
		        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		        BufferedImage image = robot.createScreenCapture(
		            new Rectangle(0, 0, screenSize.width, screenSize.height));

		        String tmpTimeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS").format(Calendar.getInstance().getTime());
		        SimpleDateFormat captureTime = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");

		        Date dateFrom = null;
		        Date dateTo = null;

		        try {
		            dateFrom = captureTime.parse(tmpTimeStamp);
		            dateTo = captureTime.parse(this.TtimeStamp);
		        } catch (ParseException e) {
		            e.printStackTrace();
		        }
		        	long dateTimeTo = dateTo.getTime();
		        	long dateTimeFrom = dateFrom.getTime();


		        	long dayDiff = ( dateTimeFrom - dateTimeTo) / (1000 );
		        	this.dayDiff = dayDiff;

		        	Date dateFrom2 = null;
		        	Date dateTo2 = null;

			        try {
			            dateFrom2 = captureTime.parse(tmpTimeStamp);
			            dateTo2 = captureTime.parse(this.TtimeStamp);
			        } catch (ParseException e) {
			            e.printStackTrace();
			        }
			        	long dateTimeTo2 = dateTo2.getTime();
			        	long dateTimeFrom2 = dateFrom2.getTime();


			        	long dayDiff2 = ( dateTimeFrom2 - dateTimeTo2) - dayDiff * 1000;


			        String tmpBookName = this.folder_name + "/" + dayDiff + "_" + dayDiff2 + ".png";
		        	//this.filename = this.TtimeStamp2 + "/" + dayDiff + "_" + dayDiff2 + ".png";

		        	if(this.file == null){
		        		file = new File(this.folder_name + "/data.json");
		        		try {
							pw = new FileWriter(file);
							System.out.println("aaa");
							pw.write("[\n");
							pw.write("  {\"time\":0 ,");
						} catch (IOException e) {
							e.printStackTrace();
						}
		        	}
		        	if(first){
			        	try {
							this.pw.write("\"name\": \""+dayDiff + "_" + dayDiff2 + ".png" + "\"},\n  {\"time\":"+ dayDiff + " ,");
						} catch (IOException e1) {
							e1.printStackTrace();
						}

				        try {
							ImageIO.write(image, "PNG", new File(tmpBookName));
						} catch (IOException e) {

							e.printStackTrace();
						}
		        	}
		        	first = true;
			}



	    }


		public void trun() {
			String TtimeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS").format(Calendar.getInstance().getTime());
			File file1 = new File(folder_name);
			if (file1.mkdir()) {

        	} else {

        	}
			this.TtimeStamp = TtimeStamp;
			this.judge = true;
		}

		public void fileClose() throws IOException{
			if(pw!=null){
				pw.close();

				FileReader fr;
				try {
					fr = new FileReader(this.folder_name + "/data.json");
					BufferedReader br = new BufferedReader(fr);
					String line = br.readLine();
					int lineCount = 1;
					while( line != null )
		            {
		                lineCount++;
		                line = br.readLine();
		            }
					/*
					String removeWord = "time\":"+ dayDiff + " ,";
					int time = 9999;
					String replaceWord = "time\":"+ time + " ,\"name\": \"\"}\n}";
					String readText = fileRead(file, lineCount-1);
					String replaceText = readText.replaceAll(removeWord, replaceWord);
					fileWrite( file, replaceText );
					System.out.printf("%s\n%s\n%s\n",replaceWord,removeWord,readText);
					System.out.printf("lineCount=%d\n",lineCount-1);
					*/
					String removeWord = "time\":"+ dayDiff + " ,";
					String replaceWord = "time\":"+ dayDiff + " ,";
					String readText = fileRead(file, lineCount-1);
					String replaceText = readText.replaceAll(removeWord, replaceWord);
					fileWrite( file, replaceText );

				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}


		}
		public static int diffrenceDays(String strDate1,String strDate2) throws ParseException{
			Date date1 = DateFormat.getDateInstance().parse(strDate1);
			Date date2 = DateFormat.getDateInstance().parse(strDate2);
			return differenceDays(date1,date2);
		}


		public static int differenceDays(Date date1,Date date2) {
		    long datetime1 = date1.getTime();
		    long datetime2 = date2.getTime();
		    long one_date_time = 1000 * 60 * 60 * 24;
		    long diffDays = (datetime1 - datetime2) / one_date_time;
		    return (int)diffDays;
		}

		private static String fileRead(File _file, int _gyo) {

	        StringBuffer fileRead = new StringBuffer("");

	        try {
	            FileReader fr = new FileReader( _file );


	            BufferedReader br = new BufferedReader( fr );

	            String str = null;

	            int count = 1;

	            while ( ( str = br.readLine() ) != null ) {

	                if ( count != _gyo) {
	                    fileRead.append(str + "\r\n");
	                } else {
	                    System.out.println( str );
	                }
	                count++;
	            }

	        } catch ( FileNotFoundException ex ) {
	             System.out.println( ex );
	        } catch ( IOException ex ) {
	             System.out.println( ex );
	        }
	        return fileRead.toString();
	    }

		 private static void fileWrite(File _file, String _text){

		        try {

		            FileWriter filewriter = new FileWriter( _file );

		            filewriter.write( _text );
		            int time = 9999;
		            filewriter.write("  {\"time\":"+ time + " ,\"name\": \"\"}\n]");

		            filewriter.close();

		        } catch ( IOException ex ) {
		             System.out.println( ex );
		        }
		 }
}

