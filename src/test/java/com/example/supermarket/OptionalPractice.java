package com.example.supermarket;

import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;

public class OptionalPractice {

    public static String returnANullString() {
        return "foo";
    }

    @Test
    public void noOptionalUsed() {
        String result  = returnANullString();
        Assert.assertTrue(result.contains("foo"));
    }

    //The point is that when we see an Optional we know it might be null and therefore we should call isPresent before
    //we go anything with it, whereas with a standard object it's hard to remember when we should do a null check and when there's no need
    @Test
    public void optionalUsed() {
        Optional<String> result = Optional.ofNullable(returnANullString());
        if(result.isPresent()){
            Assert.assertTrue(result.toString().contains("foo"));
        } else {
            Assert.assertTrue(false);
        }
    }
}
