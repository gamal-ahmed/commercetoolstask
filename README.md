
This code read a csv file lines in memory line by line to avoid out of memory exceptions in large files then parse  
these lines into order/customer entities and load these entities into two buffers (customers and orders buffers) with size less than or equal  Properties.MEMORY_MAX_LIMIT to :

<pre>-Save the memory
-Avoid uploading to server failing because of sending huge amount of data</pre>

<code>de.task.reader.CSVReader.java</code> read the CSV file from a specific path line by line into memory
<code>de.task.loader.CustomersOrdersLoader.java</code> map file lines and load data into buffers
<code>de.task.upload.Uploader.java</code> get data from memory , send it to the server , handle server response 503 failing by retrying to connect <code>Properties.MAX_TRIES</code> times and check if the file has more data not sent yet if that it invoke <code>de.task.loader.CustomersOrdersLoader</code> to load another patch until the end of the file.


<h1>Run</h1>
1. Set the application properties :MAX_TRIES,MEMORY_MAX_LIMIT,CSV_FILE_PATH,SENDER_EMAIL in <code>de.task.utils.Properties.java</code>
2. Run these command lines 
   <pre>
        $ cd coding-task-java-dev
        $ mvn package
        $ java -jar target/commercetoolstask-0.0.1-SNAPSHOT.jar
   </pre> 

<h1>Documentation</h1>
<cdoe>commercetoolstask.doc</code>

      
      
