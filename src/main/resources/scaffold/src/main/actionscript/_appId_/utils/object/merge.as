package _appId_.utils.object
{
	/**
	 * @author SamYStudiO ( contact@samystudio.net )
	 */
	public function merge( fromObject : Object, toObject : Object, overwrite : Boolean = true, excludeProperties : * = null ) : Object
	{
		toObject = toObject == null ? {} : toObject;

		excludeProperties = excludeProperties == null ? {} : excludeProperties;

		for( var s : String in fromObject )
		{
			if( ( excludeProperties is Array && ( excludeProperties as Array ).indexOf( s ) == -1 ) || ( !( excludeProperties is Array ) && excludeProperties[ s ] == undefined ) )
			{
				if( !overwrite )
				{
					var hasProperty : Boolean;

					try
					{
						hasProperty = toObject[ s ] != undefined;
					}
					catch( e : Error )
					{
						hasProperty = false;
					}

					if( hasProperty ) continue;
				}

				try
				{
					toObject[ s ] = fromObject[ s ];
				}
				catch( e : Error )
				{
				}
			}
		}

		return toObject;
	}
}
