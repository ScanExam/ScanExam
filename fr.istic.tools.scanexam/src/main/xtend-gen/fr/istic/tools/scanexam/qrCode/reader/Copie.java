package fr.istic.tools.scanexam.qrCode.reader;

import fr.istic.tools.scanexam.qrCode.reader.Page;
import java.util.HashSet;
import java.util.Set;
import org.eclipse.xtend.lib.annotations.Data;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;
import org.eclipse.xtext.xbase.lib.Pure;

@Data
@SuppressWarnings("all")
public class Copie {
  private final Set<Page> setPages;
  
  private final int numCopie;
  
  private final String studentId;
  
  private final String studentLastName;
  
  private final String studentFirstName;
  
  public Copie(final int numCopie, final int numPageInPDF, final int numPageInSubject) {
    this.numCopie = numCopie;
    HashSet<Page> _hashSet = new HashSet<Page>();
    this.setPages = _hashSet;
    Page _page = new Page(numPageInSubject, numPageInPDF);
    this.setPages.add(_page);
    this.studentId = null;
    this.studentLastName = null;
    this.studentFirstName = null;
  }
  
  public Copie(final int numCopie, final int numPageInPDF, final int numPageInSubject, final String studentId, final String studentLastName, final String studentFirstName) {
    this.numCopie = numCopie;
    HashSet<Page> _hashSet = new HashSet<Page>();
    this.setPages = _hashSet;
    Page _page = new Page(numPageInSubject, numPageInPDF);
    this.setPages.add(_page);
    this.studentId = studentId;
    this.studentLastName = studentLastName;
    this.studentFirstName = studentFirstName;
  }
  
  public Copie(final int numCopie, final Set<Page> setPages, final String studentId, final String studentLastName, final String studentFirstName) {
    this.numCopie = numCopie;
    this.setPages = setPages;
    this.studentId = studentId;
    this.studentLastName = studentLastName;
    this.studentFirstName = studentFirstName;
  }
  
  public void addInSet(final Set<Page> pages) {
    int _length = ((Object[])Conversions.unwrapArray(pages, Object.class)).length;
    ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, _length, true);
    for (final Integer i : _doubleDotLessThan) {
      this.setPages.add(((Page[])Conversions.unwrapArray(pages, Page.class))[(i).intValue()]);
    }
  }
  
  public int getNumCopie() {
    return this.numCopie;
  }
  
  public Set<Page> getPagesCopie() {
    return this.setPages;
  }
  
  public String getStudentId() {
    return this.studentId;
  }
  
  public String getStudentLastName() {
    return this.studentLastName;
  }
  
  public String getStudentFirstName() {
    return this.studentFirstName;
  }
  
  public boolean isCopyComplete(final int nbPageSubject) {
    int _length = ((Object[])Conversions.unwrapArray(this.setPages, Object.class)).length;
    return (nbPageSubject == _length);
  }
  
  public int getNumPageInPDF(final int numPage) {
    int _length = ((Object[])Conversions.unwrapArray(this.setPages, Object.class)).length;
    ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, _length, true);
    for (final Integer i : _doubleDotLessThan) {
      int _numPageInSubject = (((Page[])Conversions.unwrapArray(this.setPages, Page.class))[(i).intValue()]).getNumPageInSubject();
      boolean _equals = (_numPageInSubject == numPage);
      if (_equals) {
        return (((Page[])Conversions.unwrapArray(this.setPages, Page.class))[(i).intValue()]).getNumPageInPDF();
      }
    }
    return 0;
  }
  
  @Override
  public String toString() {
    String res = ("Copie " + Integer.valueOf(this.numCopie));
    if ((((this.studentId != null) && (this.studentLastName != null)) && (this.studentFirstName != null))) {
      String _res = res;
      res = (_res + ((((((" " + this.studentId) + " (") + this.studentLastName) + " ") + this.studentFirstName) + ")"));
    }
    String _res_1 = res;
    res = (_res_1 + " [\n");
    int _length = ((Object[])Conversions.unwrapArray(this.setPages, Object.class)).length;
    ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, _length, true);
    for (final Integer i : _doubleDotLessThan) {
      String _res_2 = res;
      String _string = (((Page[])Conversions.unwrapArray(this.setPages, Page.class))[(i).intValue()]).toString();
      String _plus = (_string + "\n");
      res = (_res_2 + _plus);
    }
    return (res + "]");
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.setPages== null) ? 0 : this.setPages.hashCode());
    result = prime * result + this.numCopie;
    result = prime * result + ((this.studentId== null) ? 0 : this.studentId.hashCode());
    result = prime * result + ((this.studentLastName== null) ? 0 : this.studentLastName.hashCode());
    return prime * result + ((this.studentFirstName== null) ? 0 : this.studentFirstName.hashCode());
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
    Copie other = (Copie) obj;
    if (this.setPages == null) {
      if (other.setPages != null)
        return false;
    } else if (!this.setPages.equals(other.setPages))
      return false;
    if (other.numCopie != this.numCopie)
      return false;
    if (this.studentId == null) {
      if (other.studentId != null)
        return false;
    } else if (!this.studentId.equals(other.studentId))
      return false;
    if (this.studentLastName == null) {
      if (other.studentLastName != null)
        return false;
    } else if (!this.studentLastName.equals(other.studentLastName))
      return false;
    if (this.studentFirstName == null) {
      if (other.studentFirstName != null)
        return false;
    } else if (!this.studentFirstName.equals(other.studentFirstName))
      return false;
    return true;
  }
  
  @Pure
  public Set<Page> getSetPages() {
    return this.setPages;
  }
}
