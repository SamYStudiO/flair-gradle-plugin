package flair.gradle.tasks.variantFactories

import flair.gradle.tasks.PublishAtlases
import flair.gradle.tasks.Tasks
import flair.gradle.variants.Variant
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class PublishAtlasesTaskFactory implements IVariantTaskFactory<PublishAtlases>
{
	public PublishAtlases create( Project project , Variant variant )
	{
		String name = Tasks.PUBLISH_ATLASES.name + variant.getNameWithType( Variant.NamingTypes.CAPITALIZE )

		PublishAtlases t = project.tasks.findByName( name ) as PublishAtlases

		if( !t ) t = project.tasks.create( name , PublishAtlases )

		t.group = Tasks.PUBLISH_ATLASES.group.name
		t.variant = variant

		return t
	}
}
