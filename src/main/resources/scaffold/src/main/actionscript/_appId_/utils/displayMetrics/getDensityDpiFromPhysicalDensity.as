package _appId_.utils.displayMetrics
{
	/**
	 * @author SamYStudiO ( contact@samystudio.net )
	 */
	public function getDensityDpiFromPhysicalDensity( density : uint ) : int
	{
		switch( true )
		{
			case density < 130 :
				return DENSITY_LDPI; // .75
			case density < 170 :
				return DENSITY_MDPI; // 1.0
			case density < 223 :
				return DENSITY_TV; // 1.33125
			case density < 250 :
				return DENSITY_HDPI; // 1.5
			case density < 290 :
				return DENSITY_280; // 1.75
			case density < 330 :
				return DENSITY_XHDPI; // 2
			case density < 370 :
				return DENSITY_360; // 2.25
			case density < 410 :
				return DENSITY_400; // 2.5
			case density < 430 :
				return DENSITY_420; // 2.625
			case density < 490 :
				return DENSITY_XXHDPI; // 3
			case density < 570 :
				return DENSITY_560; // 3.5
		}

		return DENSITY_XXXHDPI; // 4
	}
}
