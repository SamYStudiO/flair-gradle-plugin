package _appId_.utils.displayMetrics
{
	/**
	 * @author SamYStudiO ( contact@samystudio.net )
	 */
	public function getDensityBucket( density : uint ) : String
	{
		switch( true )
		{
			case density <= 140 :
				return EnumDensityBucket.LDPI; // .75
			case density <= 180 :
				return EnumDensityBucket.MDPI; // 1.0
			case density <= 260 :
				return EnumDensityBucket.HDPI; // 1.5
			case density <= 340 :
				return EnumDensityBucket.XHDPI; // 2
			case density <= 500 :
				return EnumDensityBucket.XXHDPI; // 3
		}

		return EnumDensityBucket.XXXHDPI; // 4
	}
}
