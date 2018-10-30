/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Book.Book;
import GUI.BookGUI;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

/**
 *
 * @author DuongHX
 */
public class BookControl {

    private BookGUI book;
    private ArrayList<Book> booklist;
    private DefaultComboBoxModel modelCbBox;
    private DefaultListModel modelList;
    public BookControl() {
        booklist = new ArrayList<>();//create new arraylist
        book = new BookGUI(this); //create new BookGUI
        book.setVisible(true); //setVisible(true)
        modelCbBox=new DefaultComboBoxModel();//crate new combobox model
        modelList=new DefaultListModel();//create new list model
        addYear();//load year in combo box
    }
    //save handle
    public void addOrUpdate(){
        String code=book.getTxtCode();
        String name=book.getTxtName();
        String author=book.getTxtAuthor();
        String publisher=book.getTxtPublisher();
        int year=book.getSelectedYear();
        boolean forRent=book.getCheckedBox();
        Book b=new Book(code, name, author, publisher, year, forRent);
        //first check empty 
        if(checkEmpty(code)==false && checkEmpty(name)==false && checkEmpty(author)==false && checkEmpty(publisher)==false){//if is not data is empty
            //if this book had been created
            if(checkDuplicate(b.getCode())==true){
                update(b);
                showBookInList(b);
                showMessage("Update book successful");
            }else{//if this book is new
                add(b);
                showBookInList(b);//show book in j list
                showMessage("Add book successful");
            }
        }else{//if data is empty
            showMessage("Pls enter full information");
        }
    }
    //remove handle
    public void remove(){
        int index=book.getSelectedBookIndex();
        if(index>-1){
        //remove in array list
        booklist.remove(index);
        //remove in jlist model
        modelList.remove(index);
        //then set again model
        book.setModel(modelList);
        if(booklist.size()>0){
            displayBookInformation(0);
        }  
        }else{
            showMessage("Don't have any thing to remove");
        }
    }
    //check if this book is exist return true else return false
    public boolean checkDuplicate(String code) {
        for (Book b : booklist) {
            if (b.getCode().equals(code)) {
                modelList.removeElement(b.getName());
                book.setModel(modelList);
                return true;
            }
        }
        return false;
    }

    //add book
    public void add(Book b) {
        booklist.add(b);
    }

    //update book information
    public void update(Book b) {
        for (Book bk : booklist) {
            //find book in bnook list and update
            if (bk.getCode().equals(b.getCode())) {
                bk.setCode(b.getCode());
                bk.setName(b.getName());
                bk.setAuthor(b.getAuthor());
                bk.setPublisher(b.getPublisher());
                bk.setPublishYear(b.getPublishYear());
                bk.setForRent(b.isForRent());
            }
        }
    }
    
    public void addYear(){
        //add year from 1990->2019
        for(int i=1990;i<2019;i++){
            modelCbBox.addElement(i);
        }
        //set Model cbo  box
        book.addYearCbBox(modelCbBox);
    }
    
    public void showBookInList(Book b){
        
        //add name of book in listmodel
        modelList.addElement(b.getName());
        //set mmodel jlist
        book.setModel(modelList);
    }
    //index: selected index in jlist
    public void displayBookInformation(int index){
        System.out.println(index+" pos");
        Book b=booklist.get(index);
        book.setTxtCode(b.getCode());
        book.setTxtName(b.getName());
        book.setTxtAuthor(b.getAuthor());
        book.setTxtPublisher(b.getPublisher());
        book.setYear(b.getPublishYear());
        //if true checkbox is checked
        book.setSelectedForRent(b.isForRent());
    }
    
    public void showMessage(String str) {//show  message
        JOptionPane.showMessageDialog(book, str);
    }
    public boolean checkEmpty(String s){
        return s.isEmpty();
    }
}
