package _appId_.utils.object
{
	import flash.utils.ByteArray;

	/**
	 * @author SamYStudiO ( contact@samystudio.net )
	 */
	public function copy( o : Object ) : Object
	{
		var buffer : ByteArray = new ByteArray();

		buffer.writeObject( o );
		buffer.position = 0;

		return buffer.readObject();
	}
}
