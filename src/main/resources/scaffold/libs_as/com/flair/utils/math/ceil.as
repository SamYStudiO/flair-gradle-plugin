package com.flair.utils.math
{
	/**
	 * Optimized Math.ceil
	 */
	public function ceil( n : Number ) : int
	{
		var ni : int = n;
		return ( n >= 0 && n != ni ) ? ni + 1 : ni;
	}
}
