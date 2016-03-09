package flair.gradle.tasks.variantFactories

import flair.gradle.tasks.Compile
import flair.gradle.tasks.TaskDefinition
import flair.gradle.variants.Variant
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class CompileTaskFactory implements IVariantTaskFactory<Compile>
{

	public Compile create( Project project , Variant variant )
	{
		String variantName = variant.getNameWithType( Variant.NamingType.CAPITALIZE )
		String name = TaskDefinition.COMPILE.name + variantName

		Compile t = project.tasks.findByName( name ) as Compile

		if( !t ) t = project.tasks.create( name , Compile )

		t.group = TaskDefinition.COMPILE.group.name
		t.variant = variant

		t.dependsOn TaskDefinition.PROCESS_CLASSES.name + variantName , TaskDefinition.PROCESS_LIBRARIES.name + variantName , TaskDefinition.PROCESS_AS_LIBRARIES.name + variantName , TaskDefinition.PROCESS_EXTENSIONS.name + variantName

		return t
	}
}
