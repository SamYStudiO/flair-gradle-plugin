package _appId_.utils.object
{
	/**
	 *
	 */
	public function merge( fromObject : Object , toObject : Object , overwrite : Boolean = true , excludeProperties : * = null ) : Object
	{
		toObject = !toObject ? {} : toObject;

		excludeProperties = !excludeProperties ? {} : excludeProperties;

		for( var s : String in fromObject )
		{
			var propName : String = fromObject[ s ];

			if( ( excludeProperties is Array && ( excludeProperties as Array ).indexOf( propName ) == -1 ) || ( !( excludeProperties is Array ) && excludeProperties[ propName ] == undefined ) )
			{
				if( !overwrite )
				{
					var hasProperty : Boolean;

					try
					{
						hasProperty = toObject[ propName ] != undefined;
					}
					catch( e : Error )
					{
						hasProperty = false;
					}

					if( hasProperty ) continue;
				}

				try
				{
					toObject[ propName ] = fromObject[ propName ];
				}
				catch( e : Error )
				{
				}
			}
		}

		return toObject;
	}
}
