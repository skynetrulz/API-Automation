About the tool

This tool allows users to automate REST API calls (Post and Get). This was implemented using JAVA’s Jersey framework. The tool is comprised of 
1.	Rest.jar File
The jar will read values from api.property file and invokes the API’s and save the response.

2.	api.property File
The property file contains details such as the Rest URL, Path where input files are located, the Header details (for PostStatic and Get methods) and the method.

Methods Supported

1.	Get
This method is used to automate invocation of GET calls and saves the response based on values read from api.properties file.
The api.properties file will have only Path, Headers and Method. REST Url’s will be read from input file.

2.	PostStatic
This method is used to automate invocation of POST calls (when REST URL and Headers remains the same. Only the Payload changes). The response get saved to Out folder based on values read from api.properties file.

3.	PostDynamic
This method is used to automate invocation of POST calls (when REST URL, Headers and the Payload are different). The response get saved to Out folder based on values read from api.properties file.
The api.properties file will have only Path and Method. Rest all will be read from input file/files.

Supported Error Handling Techniques

1.	Identify if the URL is syntactically incorrect
2.	Identify if the rest URL is not reachable/up
3.	Handle error responses other than 200

Advantages

1.	Hundreds of API’s can be tested, and the response could be saved in just few seconds.
2.	Retrieve the list of Failed/Success API’s in separate CSV files.
