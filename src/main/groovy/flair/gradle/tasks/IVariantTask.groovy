package flair.gradle.tasks

import flair.gradle.platforms.Platform
import org.gradle.api.Task

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
interface IVariantTask extends Task
{
	public Platform getPlatform()
	public void setPlatform( Platform value )

	public String getProductFlavor()
	public void setProductFlavor( String value )

	public String getBuildType()
	public void setBuildType( String value )
}