package fr.istic.tools.scanexam.qrCode.reader

import org.eclipse.xtend.lib.annotations.Data

@Data class Page {

	int numPageInSubject
	int numPageInPDF

	new(int numPageInSubject, int numPageInPDF) {
		this.numPageInPDF = numPageInPDF
		this.numPageInSubject = numPageInSubject

	}
}
