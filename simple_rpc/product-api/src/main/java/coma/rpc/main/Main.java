package coma.rpc.main;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.Socket;

import coma.rpc.service.IProductService;
import coma.rpc.service.bean.Product;

public class Main {
	
	public static void main(String[] args) {
		
		IProductService productService = (IProductService)rpc(IProductService.class);
		Product product = productService.queryById(100);
		System.out.println(product);
		
	}
	
	public static Object rpc(final Class clazz){
		return Proxy.newProxyInstance(clazz.getClassLoader(), 
				new Class[]{clazz}, new InvocationHandler() {
					@Override
					public Object invoke(Object proxy, Method method, Object[] args)
							throws Throwable {
						
						Socket socket = new Socket("127.0.0.1", 8888);
						
						String apiClassName = clazz.getName();
						String methodName = method.getName();
						Class[] paramterTypes = method.getParameterTypes();
						
						ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
						objectOutputStream.writeUTF(apiClassName);
						objectOutputStream.writeUTF(methodName);
						objectOutputStream.writeObject(paramterTypes);
						objectOutputStream.writeObject(args);
						objectOutputStream.flush();
						
						ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
						Object o = objectInputStream.readObject();
						objectOutputStream.close();
						objectInputStream.close();
						
						socket.close();
						return o;
					}
				});
	}
	
}
