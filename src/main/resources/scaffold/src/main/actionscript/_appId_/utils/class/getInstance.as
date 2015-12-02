package _appId_.utils.class
{
	import flash.utils.describeType;

	/**
	 * @author SamYStudiO ( contact@samystudio.net )
	 * Get an instance from a Class object.
	 * @param Clazz Class object from which get instance.
	 * @param arguments The arguments to pass to instance constructor.
	 */
	public function getInstance( Clazz : Class , ...arguments : Array ) : *
	{
		var constructorArgumentsLength : uint = describeType( Clazz ).factory.constructor.parameter.length();

		switch( constructorArgumentsLength )
		{
			case 0    :
				return new Clazz();
			case 1    :
				return new Clazz( arguments[ 0 ] );
			case 2    :
				return new Clazz( arguments[ 0 ] , arguments[ 1 ] );
			case 3    :
				return new Clazz( arguments[ 0 ] , arguments[ 1 ] , arguments[ 2 ] );
			case 4    :
				return new Clazz( arguments[ 0 ] , arguments[ 1 ] , arguments[ 2 ] , arguments[ 3 ] );
			case 5    :
				return new Clazz( arguments[ 0 ] , arguments[ 1 ] , arguments[ 2 ] , arguments[ 3 ] , arguments[ 4 ] );
			case 6    :
				return new Clazz( arguments[ 0 ] , arguments[ 1 ] , arguments[ 2 ] , arguments[ 3 ] , arguments[ 4 ] , arguments[ 5 ] );
			case 7    :
				return new Clazz( arguments[ 0 ] , arguments[ 1 ] , arguments[ 2 ] , arguments[ 3 ] , arguments[ 4 ] , arguments[ 5 ] , arguments[ 6 ] );
			case 8    :
				return new Clazz( arguments[ 0 ] , arguments[ 1 ] , arguments[ 2 ] , arguments[ 3 ] , arguments[ 4 ] , arguments[ 5 ] , arguments[ 6 ] , arguments[ 7 ] );
			case 9    :
				return new Clazz( arguments[ 0 ] , arguments[ 1 ] , arguments[ 2 ] , arguments[ 3 ] , arguments[ 4 ] , arguments[ 5 ] , arguments[ 6 ] , arguments[ 7 ] , arguments[ 8 ] );
			case 10 :
				return new Clazz( arguments[ 0 ] , arguments[ 1 ] , arguments[ 2 ] , arguments[ 3 ] , arguments[ 4 ] , arguments[ 5 ] , arguments[ 6 ] , arguments[ 7 ] , arguments[ 8 ] , arguments[ 9 ] );
			case 11 :
				return new Clazz( arguments[ 0 ] , arguments[ 1 ] , arguments[ 2 ] , arguments[ 3 ] , arguments[ 4 ] , arguments[ 5 ] , arguments[ 6 ] , arguments[ 7 ] , arguments[ 8 ] , arguments[ 9 ] , arguments[ 10 ] );
			case 12 :
				return new Clazz( arguments[ 0 ] , arguments[ 1 ] , arguments[ 2 ] , arguments[ 3 ] , arguments[ 4 ] , arguments[ 5 ] , arguments[ 6 ] , arguments[ 7 ] , arguments[ 8 ] , arguments[ 9 ] , arguments[ 10 ] , arguments[ 11 ] );
			case 13 :
				return new Clazz( arguments[ 0 ] , arguments[ 1 ] , arguments[ 2 ] , arguments[ 3 ] , arguments[ 4 ] , arguments[ 5 ] , arguments[ 6 ] , arguments[ 7 ] , arguments[ 8 ] , arguments[ 9 ] , arguments[ 10 ] , arguments[ 11 ] , arguments[ 12 ] );
			case 14 :
				return new Clazz( arguments[ 0 ] , arguments[ 1 ] , arguments[ 2 ] , arguments[ 3 ] , arguments[ 4 ] , arguments[ 5 ] , arguments[ 6 ] , arguments[ 7 ] , arguments[ 8 ] , arguments[ 9 ] , arguments[ 10 ] , arguments[ 11 ] , arguments[ 12 ] , arguments[ 13 ] );
			case 15 :
				return new Clazz( arguments[ 0 ] , arguments[ 1 ] , arguments[ 2 ] , arguments[ 3 ] , arguments[ 4 ] , arguments[ 5 ] , arguments[ 6 ] , arguments[ 7 ] , arguments[ 8 ] , arguments[ 9 ] , arguments[ 10 ] , arguments[ 11 ] , arguments[ 12 ] , arguments[ 13 ] , arguments[ 14 ] );
			case 16 :
				return new Clazz( arguments[ 0 ] , arguments[ 1 ] , arguments[ 2 ] , arguments[ 3 ] , arguments[ 4 ] , arguments[ 5 ] , arguments[ 6 ] , arguments[ 7 ] , arguments[ 8 ] , arguments[ 9 ] , arguments[ 10 ] , arguments[ 11 ] , arguments[ 12 ] , arguments[ 13 ] , arguments[ 14 ] , arguments[ 15 ] );
			case 17 :
				return new Clazz( arguments[ 0 ] , arguments[ 1 ] , arguments[ 2 ] , arguments[ 3 ] , arguments[ 4 ] , arguments[ 5 ] , arguments[ 6 ] , arguments[ 7 ] , arguments[ 8 ] , arguments[ 9 ] , arguments[ 10 ] , arguments[ 11 ] , arguments[ 12 ] , arguments[ 13 ] , arguments[ 14 ] , arguments[ 15 ] , arguments[ 16 ] );
			case 18 :
				return new Clazz( arguments[ 0 ] , arguments[ 1 ] , arguments[ 2 ] , arguments[ 3 ] , arguments[ 4 ] , arguments[ 5 ] , arguments[ 6 ] , arguments[ 7 ] , arguments[ 8 ] , arguments[ 9 ] , arguments[ 10 ] , arguments[ 11 ] , arguments[ 12 ] , arguments[ 13 ] , arguments[ 14 ] , arguments[ 15 ] , arguments[ 16 ] , arguments[ 17 ] );
			case 19 :
				return new Clazz( arguments[ 0 ] , arguments[ 1 ] , arguments[ 2 ] , arguments[ 3 ] , arguments[ 4 ] , arguments[ 5 ] , arguments[ 6 ] , arguments[ 7 ] , arguments[ 8 ] , arguments[ 9 ] , arguments[ 10 ] , arguments[ 11 ] , arguments[ 12 ] , arguments[ 13 ] , arguments[ 14 ] , arguments[ 15 ] , arguments[ 16 ] , arguments[ 17 ] , arguments[ 18 ] );
			case 20 :
				return new Clazz( arguments[ 0 ] , arguments[ 1 ] , arguments[ 2 ] , arguments[ 3 ] , arguments[ 4 ] , arguments[ 5 ] , arguments[ 6 ] , arguments[ 7 ] , arguments[ 8 ] , arguments[ 9 ] , arguments[ 10 ] , arguments[ 11 ] , arguments[ 12 ] , arguments[ 13 ] , arguments[ 14 ] , arguments[ 15 ] , arguments[ 16 ] , arguments[ 17 ] , arguments[ 18 ] , arguments[ 19 ] );
			case 21 :
				return new Clazz( arguments[ 0 ] , arguments[ 1 ] , arguments[ 2 ] , arguments[ 3 ] , arguments[ 4 ] , arguments[ 5 ] , arguments[ 6 ] , arguments[ 7 ] , arguments[ 8 ] , arguments[ 9 ] , arguments[ 10 ] , arguments[ 11 ] , arguments[ 12 ] , arguments[ 13 ] , arguments[ 14 ] , arguments[ 15 ] , arguments[ 16 ] , arguments[ 17 ] , arguments[ 18 ] , arguments[ 19 ] , arguments[ 20 ] );
		}
	}
}
