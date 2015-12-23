package com.flair.utils.device
{
	import flash.system.Capabilities;

	/**
	 *
	 */
	public function isLinux() : Boolean
	{
		return Capabilities.manufacturer.toLowerCase().indexOf( "linux" ) >= 0;
	}
}
