package fr.istic.tools.scanexam.io

import fr.istic.tools.scanexam.core.templates.CorrectionTemplate
import java.util.Optional
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl
import fr.istic.tools.scanexam.core.templates.TemplatesPackage
import org.eclipse.emf.common.util.URI
import fr.istic.tools.scanexam.core.templates.CreationTemplate
import org.eclipse.emf.ecore.EObject
import fr.istic.tools.scanexam.core.CorePackage
import java.io.File

class TemplateIO 
{
	def static Optional<CorrectionTemplate> loadCorrectionTemplate(String path) 
	{
        val resourceSet = new ResourceSetImpl();
        val _extensionToFactoryMap = resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap()
        val _xMIResourceFactoryImpl = new XMIResourceFactoryImpl();
        _extensionToFactoryMap.put(Resource.Factory.Registry.DEFAULT_EXTENSION, _xMIResourceFactoryImpl)

        resourceSet.getPackageRegistry().put(TemplatesPackage.eNS_URI, TemplatesPackage.eINSTANCE);

        var Resource resource = null;
        try {
            resource = resourceSet.getResource(URI.createFileURI(path), true)
        } catch (Throwable ex) {
            return Optional.empty;
        }
        val template = resource.getContents().get(0);
        if (!(template instanceof CorrectionTemplate))
        {
            return Optional.empty;
        }
        return Optional.ofNullable(template as CorrectionTemplate)
    }	
    
    def static Optional<CreationTemplate> loadCreationTemplate(String path) 
    {
        val resourceSet = new ResourceSetImpl();
        val _extensionToFactoryMap = resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap()
        val _xMIResourceFactoryImpl = new XMIResourceFactoryImpl();
        _extensionToFactoryMap.put(Resource.Factory.Registry.DEFAULT_EXTENSION, _xMIResourceFactoryImpl)

        resourceSet.getPackageRegistry().put(TemplatesPackage.eNS_URI, TemplatesPackage.eINSTANCE);

        var Resource resource = null;

        try {
            resource = resourceSet.getResource(URI.createFileURI(path), true)
        } catch (Throwable ex) {
            return Optional.empty;
        }
        
        val template = resource.getContents().get(0);
        
        if (!(template instanceof CreationTemplate))
        {
            return Optional.empty;
        }
        
        return Optional.ofNullable(template as CreationTemplate)
    }
    
    
    def static void save(File file,EObject obj)
    {
		val resourceSet = new ResourceSetImpl();
		val _extensionToFactoryMap = resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap()
		val _xMIResourceFactoryImpl = new XMIResourceFactoryImpl()
		_extensionToFactoryMap.put(Resource.Factory.Registry.DEFAULT_EXTENSION, _xMIResourceFactoryImpl)
			resourceSet.getPackageRegistry().put(CorePackage.eNS_URI, CorePackage.eINSTANCE);
		resourceSet.getPackageRegistry().put(TemplatesPackage.eNS_URI, TemplatesPackage.eINSTANCE);
	
			
		val resource = resourceSet.createResource(URI.createFileURI(file.absolutePath))
		resource.getContents().add(obj);
		resource.save(null);
	}
}