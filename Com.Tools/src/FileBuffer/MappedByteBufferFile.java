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
* @Description: ͨ��ӳ�䷽ʽ���ļ����ж�д 
* @author jinyu
* @date 2016��10��31�� ����12:26:33 
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
	* ����һ���µ�ʵ�� MappedByteBufferFile.    
	*    
	* @param fileName �ļ����ƣ�����·����
	* @param size �ļ�����
	 */
public MappedByteBufferFile(String fileName,int size)
{
	curfile=fileName;
	bufferSize=size;
	
}
/**
 * 
* @Name: CreateFile 
* @Description: �����ļ�
* @return void    �������� 
* @throws
 */
private void CreateFile()
{
	RandomAccessFile raf = null;
	try {
	
		//����һ��ָ����С�Ŀ��ļ�
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
* @Description: д���ļ�
* @param data  ����˵�� 
* @return void    �������� 
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
* @Description: ��ȡ�ļ�
* @return  ����˵�� 
* @return byte[]    �������� 
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
* @Description: �ر��ļ���д
* @return void    �������� 
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
* @Description: �ر��ļ�д��
* @return void    �������� 
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
