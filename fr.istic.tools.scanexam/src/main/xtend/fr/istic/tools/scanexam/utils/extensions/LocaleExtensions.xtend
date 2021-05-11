package fr.istic.tools.scanexam.utils.extensions

import java.util.Locale

class LocaleExtensions 
{
	/**
	 * @param un locale
	 * @return le displayname du locale dans sa propre langue 
	 */
	def static String capitalizeDisplayName(Locale locale)
	{
		val name = locale.getDisplayName(locale)
		return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase()
	}	
}