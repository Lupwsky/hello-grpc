import com.grpc.nospring.GrpcServer;
import com.grpc.nospring.GrpcServerInterceptor;
import com.grpc.nospring.TestServiceGrpcImpl;

/**
 * Company: wesure
 * Project Name: hello-grpc
 * Description:
 *
 * @author v_pwlu
 * @date 2018/6/21
 */
public class MainServer {
    public static void main(String[] args) {
        TestServiceGrpcImpl testServiceGrpc = new TestServiceGrpcImpl();
        GrpcServerInterceptor serverInterceptor = new GrpcServerInterceptor();
        GrpcServer server = new GrpcServer(8000, testServiceGrpc, serverInterceptor);
        server.blockUntilShutdown();
        System.out.println("进程结束!");
    }
}
