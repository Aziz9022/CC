Practical No. 4 AIM: RMI based application program to display current date and time.
1.
ServerDate.java
import java.rmi.*;
import java.rmi.server.*;
import java.util.*;
public class ServerDate extends UnicastRemoteObject implements InterDate
{
public ServerDate() throws Exception
{
}
public String display() throws Exception
{
String str = "";
Date d = new Date();
str = d.toString();
return str;
}
public static void main(String args[]) throws Exception
{
ServerDate s1 = new ServerDate();
Naming.bind("DS", s1);
System.out.println("Object registered.....");
}
}
2.
ClientDate.java
import java.rmi.*;
import java.io.*;
public class ClientDate
{
public static void main(String args[]) throws Exception
{
String s1;
InterDate h1 = (InterDate)Naming.lookup("DS");
s1 = h1.display();
System.out.println(s1);
}
}
3.
InterDate.java
import java.rmi.*;
public interface InterDate extends Remote
{
public String display() throws Exception;
} explain this code to me so that i can write it in tomorrow exam
