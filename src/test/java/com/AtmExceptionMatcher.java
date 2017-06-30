package com;

import com.atm_exeption.AtmException;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class AtmExceptionMatcher extends TypeSafeMatcher<AtmException> {

    private AtmException atmException;

    public AtmExceptionMatcher(AtmException atmException) {
        this.atmException = atmException;
    }

    protected boolean matchesSafely(AtmException e) {
       return atmException.getErrorCodes().equals(e.getErrorCodes());
    }

    public void describeTo(Description description) {

    }
}
