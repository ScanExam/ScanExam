package fr.istic.tools.scanexam.qrCode.reader;

import org.eclipse.xtend.lib.annotations.Data;
import org.eclipse.xtext.xbase.lib.Pure;

@Data
@SuppressWarnings("all")
public class Page {
  private final int numPageInSubject;
  
  private final int numPageInPDF;
  
  public Page(final int numPageInSubject, final int numPageInPDF) {
    this.numPageInPDF = numPageInPDF;
    this.numPageInSubject = numPageInSubject;
  }
  
  public int getNumPageInSubject() {
    return this.numPageInSubject;
  }
  
  public int getNumPageInPDF() {
    return this.numPageInPDF;
  }
  
  @Override
  public String toString() {
    return (((("[" + Integer.valueOf(this.numPageInSubject)) + ", ") + Integer.valueOf(this.numPageInPDF)) + "]");
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + this.numPageInSubject;
    return prime * result + this.numPageInPDF;
  }
  
  @Override
  @Pure
  public boolean equals(final Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Page other = (Page) obj;
    if (other.numPageInSubject != this.numPageInSubject)
      return false;
    if (other.numPageInPDF != this.numPageInPDF)
      return false;
    return true;
  }
}
