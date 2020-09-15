package fr.istic.tools.scanexam.instances;

import fr.istic.tools.scanexam.Exam;
import fr.istic.tools.scanexam.ScanexamPackage;
import java.io.IOException;
import java.util.Map;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

@SuppressWarnings("all")
public class ExamIO {
  public static void save(final Exam datapath, final String modelfilename) throws IOException {
    ResourceSet resourceSet = new ResourceSetImpl();
    Map<String, Object> _extensionToFactoryMap = resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap();
    XMIResourceFactoryImpl _xMIResourceFactoryImpl = new XMIResourceFactoryImpl();
    _extensionToFactoryMap.put(
      Resource.Factory.Registry.DEFAULT_EXTENSION, _xMIResourceFactoryImpl);
    resourceSet.getPackageRegistry().put(ScanexamPackage.eNS_URI, ScanexamPackage.eINSTANCE);
    Resource resource = resourceSet.createResource(URI.createFileURI(modelfilename));
    resource.getContents().add(datapath);
    resource.save(null);
  }
  
  public static Exam load(final String modelfilename) {
    ResourceSet resourceSet = new ResourceSetImpl();
    Map<String, Object> _extensionToFactoryMap = resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap();
    XMIResourceFactoryImpl _xMIResourceFactoryImpl = new XMIResourceFactoryImpl();
    _extensionToFactoryMap.put(
      Resource.Factory.Registry.DEFAULT_EXTENSION, _xMIResourceFactoryImpl);
    resourceSet.getPackageRegistry().put(ScanexamPackage.eNS_URI, ScanexamPackage.eINSTANCE);
    Resource resource = resourceSet.getResource(URI.createFileURI(modelfilename), true);
    EObject _get = resource.getContents().get(0);
    return ((Exam) _get);
  }
}
