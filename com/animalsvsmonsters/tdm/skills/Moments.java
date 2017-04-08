package com.animalsvsmonsters.tdm.skills;

import java.util.HashMap;
import java.util.Map;

import com.animalsvsmonsters.tdm.skills.timers.SkillTimer;

/**
 * Basically just an interface that gives provides a map of skill timers under certain labels.
 * 
 * @author Justis
 *
 */
public abstract interface Moments {
	public static final Map<String, SkillTimer> timers = new HashMap<>();
}
