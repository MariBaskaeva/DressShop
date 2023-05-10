package ru.baskaeva.dressshop.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoSuchProductException extends Exception{
    public NoSuchProductException(){
        super();
    }
    public NoSuchProductException(String msg){
        super(msg);
    }
}
