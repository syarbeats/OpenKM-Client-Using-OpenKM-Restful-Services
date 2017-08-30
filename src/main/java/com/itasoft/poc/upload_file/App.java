package com.itasoft.poc.upload_file;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;


public class App 
{
    public static void main( String[] args )
    {
    	Options options = new Options();
    	
    	Option username = new Option("u", "username", true, "username");
        username.setRequired(true);
        options.addOption(username);

        Option password = new Option("p", "password", true, "password");
        password.setRequired(true);
        options.addOption(password);

        Option docPath = new Option("d", "docPath", true, "file path");
        docPath.setRequired(true);
        options.addOption(docPath);

        Option content = new Option("c", "content", true, "content file");
        content.setRequired(true);
        options.addOption(content);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd = null;

        try {
			cmd = parser.parse(options, args);
		} catch (ParseException e) {
			e.printStackTrace();
		}

        String docFilePath = cmd.getOptionValue("docPath");
        String contentDocFile = cmd.getOptionValue("content");
        String usernameCmd = cmd.getOptionValue("username");
        String passwordCmd = cmd.getOptionValue("password");

        System.out.println(docFilePath);
        System.out.println(contentDocFile);
    	
    	String charset = "UTF-8";
    	File contentFile = new File(contentDocFile);
    	
    	String requestURL = "http://localhost:8080/OpenKM/services/rest/document/createSimple";

		try {
			MultipartUtility multipart = new MultipartUtility(requestURL, charset, usernameCmd, passwordCmd);
			
			multipart.addHeaderField("User-Agent", "poc-jamkrindo");
			multipart.addHeaderField("Test-Header", "Header-Value");
			multipart.addFormField("docPath", docFilePath);
			multipart.addFilePart("content", contentFile);
			List<String> response = multipart.finish();
			
			for (String line : response) {
				System.out.println(line);
			}
			
		} catch (IOException ex) {
			System.err.println(ex);
		}
    }
}
