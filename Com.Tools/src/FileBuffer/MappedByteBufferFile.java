/**
 * 
 */
package FileBuffer;


import java.io.FileNotFoundException;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/** 
* 
* @Description: 通过映射方式对文件进行读写 
* @author jinyu
* @date 2016年10月31日 上午12:26:33 
*  
*/
public class MappedByteBufferFile {
	String curfile;
	int bufferSize;
	long index=0;
	boolean preWrite=true;
	RandomAccessFile memoryMappedFile = null;
	byte[]dstread=null;

	/**
	 * 
	* 创建一个新的实例 MappedByteBufferFile.    
	*    
	* @param fileName 文件名称（包括路径）
	* @param size 文件长度
	 */
public MappedByteBufferFile(String fileName,int size)
{
	curfile=fileName;
	bufferSize=size;
	
}
/**
 * 
* @Name: CreateFile 
* @Description: 创建文件
* @return void    返回类型 
* @throws
 */
private void CreateFile()
{
	RandomAccessFile raf = null;
	try {
	
		//建立一个指定大小的空文件
		raf = new RandomAccessFile(curfile, "rw");
		raf.setLength(bufferSize);
	    
	} catch (Exception e) {
	} finally {
		if ( raf != null ) {
			try {
				raf.close();
			} catch (Exception e2) {
			}
		}
	}
	
}
/**
 * 
* @Name: Write 
* @Description: 写入文件
* @param data  参数说明 
* @return void    返回类型 
* @throws
 */
public void Write(byte[]data)
{ 
	if(preWrite)
	{
		preWrite=false;
		CreateFile();
	}
	MappedByteBuffer out;
	if(memoryMappedFile==null)
	{
	try {
		memoryMappedFile = new RandomAccessFile(curfile, "rw");
	} catch (FileNotFoundException e) {
		
		e.printStackTrace();
		return;
	}
	}

try {
	out = memoryMappedFile.getChannel().map(FileChannel.MapMode.READ_WRITE, index, data.length);
	out.put(data);
	out.force();
} catch (IOException e) {
	
	e.printStackTrace();
}
index+=data.length;

}
/**
 * 
* @Name: Read 
* @Description: 读取文件
* @return  参数说明 
* @return byte[]    返回类型 
* @throws
 */
public byte[] Read()
{
	MappedByteBuffer out;
	long leftsize=0;
//	if(dstread==null)
//	{
//		dstread=new byte[bufferSize];
//	}
	if(memoryMappedFile==null)
	{
	try {
		memoryMappedFile = new RandomAccessFile(curfile, "rw");
	} catch (FileNotFoundException e) {
		
		e.printStackTrace();
		return null;
	}
	}
	//
	try {
		if(index+bufferSize<memoryMappedFile.length())
		{
			leftsize=bufferSize;
		}
		else
		{
			leftsize=memoryMappedFile.length()-index;
		}
		
		out = memoryMappedFile.getChannel().map(FileChannel.MapMode.READ_WRITE, index,leftsize);
		  return	out.array();
	} catch (IOException e) {
		 return null;
		
	}
  
	
}
/**
 * 
* @Name: Close 
* @Description: 关闭文件读写
* @return void    返回类型 
* @throws
 */
public void Close()
{
	try {
		memoryMappedFile.close();
		RandomAccessFile raf = new RandomAccessFile(curfile, "rw");
		
		raf.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
/**
 * 
* @Name: WriteClose 
* @Description: 关闭文件写入
* @return void    返回类型 
* @throws
 */
public void WriteClose()
{
	try {
		memoryMappedFile.close();
		RandomAccessFile raf = new RandomAccessFile(curfile, "rw");
		raf.setLength(index);
		raf.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
}
