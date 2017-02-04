package PublicModel;

/**
 * 服务注册信息,中间信息
 * 相比Sever_BindsInfo
 * 向客户端发送的信息
 * 没有sid,communicationType
 * @author jinyu
 *
 */
public class ServerBinds {
	
  /**
 * 服务地址
 */
public String address="127.0.0.1";

  /**
 * 服务端口
 */
public int port=4444;

  /**
 * 服务名称
 */
public String name="初始化验证";

  /**
 * 启用的通信协议（TCP,UDP）
 */
public String communicationType="TCP";

  /**
 * 服务唯一值，用于客户端标已经接受过的信息
 */
public String sid;

/**
 * 确定是否是主服务
 * 0不是1是
 */
public String master="0";

/**
 * 是否启用
 * 1启用0否
 * 只针对主从服务时的主服务
 */
public String Is_Using="1";

/**
 * 
 */
public String slave="0";
}
