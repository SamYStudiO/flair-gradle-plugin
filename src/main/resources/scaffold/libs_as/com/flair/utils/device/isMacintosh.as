package com.flair.utils.device
{
	import flash.system.Capabilities;

	/**
	 *
	 */
	public function isMacintosh() : Boolean
	{
		return Capabilities.manufacturer.toLowerCase().indexOf( "macintosh" ) >= 0;
	}
}
