package com.ins.web;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BottomsUpApplication implements CommandLineRunner{
	
//	@Autowired
//	private MasterUserService masterUserService;
//	
    public static void main(String[] args) {
        SpringApplication.run(BottomsUpApplication.class, args);
    }

    public void run(String... args) throws Exception {
        System.out.println("Starting code");
          
//        MasterUserVo user = new MasterUserVo();
//        user.setName("Dinesh");
//        user.setCompany("Aseuro");
//        user.setLocation("Bangalore");
//        user.setManager("sharan");
//        user.setRates(23.44);
       
//        MasterUserVo user1 = this.masterUserService.newUser(user);
//        System.out.println(user1.getName());
    }
}
