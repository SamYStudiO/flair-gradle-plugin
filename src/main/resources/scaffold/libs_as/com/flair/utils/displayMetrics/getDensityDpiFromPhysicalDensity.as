package com.flair.utils.displayMetrics
{
	/**
	 * Get density dpi bucket (mdpi 160, hdpi 240, xhdpi 320, etc...) from specified dpi.
	 */
	public function getDensityDpiFromPhysicalDensity( density : uint ) : int
	{
		switch( true )
		{
			case density < 130 :
				return EnumDensityDpi.DENSITY_LDPI; // .75
			case density < 170 :
				return EnumDensityDpi.DENSITY_MDPI; // 1.0
			case density < 223 :
				return EnumDensityDpi.DENSITY_TV; // 1.33125
			case density < 250 :
				return EnumDensityDpi.DENSITY_HDPI; // 1.5
			case density < 290 :
				return EnumDensityDpi.DENSITY_280; // 1.75
			case density < 330 :
				return EnumDensityDpi.DENSITY_XHDPI; // 2
			case density < 370 :
				return EnumDensityDpi.DENSITY_360; // 2.25
			case density < 410 :
				return EnumDensityDpi.DENSITY_400; // 2.5
			case density < 430 :
				return EnumDensityDpi.DENSITY_420; // 2.625
			case density < 490 :
				return EnumDensityDpi.DENSITY_XXHDPI; // 3
			case density < 570 :
				return EnumDensityDpi.DENSITY_560; // 3.5
		}

		return EnumDensityDpi.DENSITY_XXXHDPI; // 4
	}
}
