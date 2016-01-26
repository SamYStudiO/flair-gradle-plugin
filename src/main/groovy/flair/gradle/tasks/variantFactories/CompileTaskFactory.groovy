package flair.gradle.tasks.variantFactories

import flair.gradle.tasks.Compile
import flair.gradle.tasks.Tasks
import flair.gradle.variants.Variant
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class CompileTaskFactory implements IVariantTaskFactory<Compile>
{

	public Compile create( Project project , Variant variant )
	{
		String variantName = variant.getNameWithType( Variant.NamingTypes.CAPITALIZE )
		String name = Tasks.COMPILE.name + variantName

		Compile t = project.tasks.findByName( name ) as Compile

		if( !t ) t = project.tasks.create( name , Compile )

		t.group = Tasks.COMPILE.group.name
		t.variant = variant

		t.dependsOn Tasks.PROCESS_CLASSES.name + variantName , Tasks.PROCESS_LIBRARIES.name + variantName

		return t
	}
}
