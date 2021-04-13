package fr.istic.tools.scanexam.filter

import fr.istic.tools.scanexam.filter.filter.BasicFilter

interface FilterFactory {
	
	def BasicFilter<?> createFilter()
	
}