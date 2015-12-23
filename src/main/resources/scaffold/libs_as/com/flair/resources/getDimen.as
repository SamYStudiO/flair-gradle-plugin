package com.flair.resources
{
	import com.flair.utils.device.isAndroid;
	import com.flair.utils.displayMetrics.getDensityScale;
	import _appId_.view.EnumScreen;

	import com.flair.PhysicalDensity;

	import feathers.system.DeviceCapabilities;

	/**
	 *
	 */
	public function getDimen( id : String , groupID : String = EnumScreen.MAIN ) : Number
	{
		var dim : String = getAssetManager( groupID ).getXml( "values" )..dimen.( @name == id ).toString();
		var value : int = parseInt( dim );
		var matches : Array = dim.match( /^\d([a-z]+)/ );

		if( matches && matches.length )
		{
			var unit : String = matches[ 0 ];

			switch( unit )
			{
				case "dp" :
					return value;
				case "px" :
					return value / getDensityScale();
				case "mm" :
				case "in" :

					var dpi : Number = isAndroid() ? PhysicalDensity.dpi : DeviceCapabilities.dpi;
					var inches : Number = value * dpi / getDensityScale();

					if( unit == "in" ) return inches;

					var mm : Number = inches * 0.0393701;

					return mm;
			}
		}
		else return NaN;
	}
}
