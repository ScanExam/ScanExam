package fr.istic.tools.scanexam.api

interface Observer<T> {
	
	def void update(Subject<T> subject, T data)
	
}