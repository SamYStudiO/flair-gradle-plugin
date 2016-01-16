package flair.gradle.tasks

import flair.gradle.platforms.Platform
import org.gradle.api.DefaultTask

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
class AbstractVariantTask extends DefaultTask implements IVariantTask
{
	protected Platform platform

	public Platform getPlatform()
	{
		return platform
	}

	public void setPlatform( Platform value )
	{
		platform = value
	}

	protected String productFlavor

	public String getProductFlavor()
	{
		return productFlavor
	}

	public void setProductFlavor( String value )
	{
		productFlavor = value
	}

	protected String buildType

	public String getBuildType()
	{
		return buildType
	}

	public void setBuildType( String value )
	{
		buildType = value
	}
}
