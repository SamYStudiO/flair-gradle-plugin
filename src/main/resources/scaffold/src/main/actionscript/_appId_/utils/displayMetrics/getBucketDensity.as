package _appId_.utils.displayMetrics
{
	/**
	 * @author SamYStudiO ( contact@samystudio.net )
	 */
	public function getBucketDensity( bucket : String ) : uint
	{
		switch( bucket )
		{
			case EnumDensityBucket.LDPI :
				return 120; // .75
			case EnumDensityBucket.MDPI :
				return 160; // 1.0
			case EnumDensityBucket.HDPI :
				return 240; // 1.5
			case EnumDensityBucket.XHDPI :
				return 320; // 2
			case EnumDensityBucket.XXHDPI :
				return 480; // 3
			case EnumDensityBucket.XXXHDPI :
				return 640; // 4
		}

		return 160;
	}
}
