package flair.gradle.tasks.variantFactories

import flair.gradle.tasks.Groups
import flair.gradle.tasks.PublishAtlases
import flair.gradle.variants.Variant
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class PublishAtlasesTaskFactory implements IVariantTaskFactory<PublishAtlases>
{
	public PublishAtlases create( Project project , Variant variant )
	{
		String name = "publishAtlases" + variant.name

		PublishAtlases t = project.tasks.findByName( name ) as PublishAtlases

		if( !t ) t = project.tasks.create( name , PublishAtlases )

		t.group = Groups.TEXTURE_PACKER.name
		t.variant = variant

		return t
	}
}
