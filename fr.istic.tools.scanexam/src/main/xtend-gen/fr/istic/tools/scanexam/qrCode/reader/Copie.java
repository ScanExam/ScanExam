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
  
  public Copie(final int numCopie, final int numPageInPDF, final int numPageInSubject) {
    this.numCopie = numCopie;
    HashSet<Page> _hashSet = new HashSet<Page>();
    this.setPages = _hashSet;
    Page _page = new Page(numPageInSubject, numPageInPDF);
    this.setPages.add(_page);
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
    String res = (("Copie " + Integer.valueOf(this.numCopie)) + "[\n");
    int _length = ((Object[])Conversions.unwrapArray(this.setPages, Object.class)).length;
    ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, _length, true);
    for (final Integer i : _doubleDotLessThan) {
      String _res = res;
      String _string = (((Page[])Conversions.unwrapArray(this.setPages, Page.class))[(i).intValue()]).toString();
      String _plus = (_string + "\n");
      res = (_res + _plus);
    }
    return (res + "]");
  }
  
  @Override
  @Pure
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.setPages== null) ? 0 : this.setPages.hashCode());
    return prime * result + this.numCopie;
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
    return true;
  }
  
  @Pure
  public Set<Page> getSetPages() {
    return this.setPages;
  }
}
