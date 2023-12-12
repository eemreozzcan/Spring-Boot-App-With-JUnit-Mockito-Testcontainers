package com.eemrezcn.education.exception;

/*The "Resourcenotfoundexception" Class Represents An Error Situation That Occurs When A Resource Is Not Found,
And It Can Be Used To Handle This Situation In A More Meaningful Way.*/
public class ResourceNotFoundException extends RuntimeException {

    /*It Takes An Error Message As A Parameter And Creates An Exception Forwarded To The Superclass (RuntimeException) Using This Message*/
    public ResourceNotFoundException(String message) {
        super(message);
    }

    /*This Constructor Creates An Exception Forwarded To The Superclass (Runtimeexception) Using The Specified Message And Another Exception That Caused */
    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);

    }

}
