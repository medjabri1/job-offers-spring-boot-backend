package com.mjrdev.JobOffers.Utility;

import java.util.Map;

import static java.util.Map.entry;

public class Utility {

    public static final Map<String, String> USER_ROLES = Map.ofEntries(
            entry("W", "WORKER"),
            entry("R", "RECRUITER"),
            entry("A", "ADMIN")
    );

}
