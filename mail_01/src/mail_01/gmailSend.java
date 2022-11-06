package mail_01;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//import java.io.UnsupportedEncodingException;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

//import java.util.Scanner;

//import javax.mail.MessagingException;
import javax.mail.Multipart;
//import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
//import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
//import javax.activation.FileDataSource;
import javax.activation.DataHandler;

public class gmailSend {
	//GUI 생성
	public static void GUI() {
		Dimension dim = new Dimension(400, 100);
		JFrame frame = new JFrame("이메일 전송 기능");
		frame.setLocation(200, 400);
		frame.setPreferredSize(dim);
		
		JTextField textfield = new JTextField();
		//textfield.setText("이메일 입력란");
		
		JLabel label = new JLabel("전송 받고 싶은 이메일을 입력하세요");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setVerticalAlignment(SwingConstants.CENTER);
		
		JButton btn = new JButton("이메일 발송");
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//label.setText(textfield.getText());
				String Email = textfield.getText();
				run(Email);
			}
		});
		
		frame.add(textfield, BorderLayout.CENTER);
		frame.add(label, BorderLayout.NORTH);
		frame.add(btn, BorderLayout.SOUTH);
		
		frame.pack();
		frame.setVisible(true);
	}
	public static void run (String Email) {
		/* 일단은 스캐너 쓰고 gui는 이따가
    	Scanner sc = new Scanner(System.in);
    	System.out.print("Enter your Email : ");
        String recipient = sc.nextLine();
        sc.close();
    	 */
    	String recipient = Email;
    	
        // 1. 발신자의 메일 계정과 비밀번호 설정함
        final String user = "MJUairportFile@gmail.com";
        final String password = "kpflpgpvgvxxzcxh";
 
        // 2. Property에 SMTP 서버 정보 설정
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", 465);
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.ssl.enable", "true");
        prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");
 
        // 3. SMTP 서버정보와 사용자 정보를 기반으로 Session 클래스의 인스턴스 생성
        Session session = Session.getDefaultInstance(prop, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, password);
            }
        });
 
        // 4. Message 클래스의 객체를 사용하여 수신자와 내용, 제목의 메시지를 작성한다.
       
        // 5. Transport 클래스를 사용하여 작성한 메세지를 전달한다
        MimeMessage message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(user));
 
            // 수신자 메일 주소
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
 
            // 제목
            message.setSubject("고급객체지향 공항 정보");
            // 내용
        	String content = "이 메일은 명지대 고급객체지향 프로그래밍 프로젝트에서 구현된 메일 전송 기능으로,"
            		+ " 엑셀 파일에서 추출된 정보를 첨부파일로 받아올 수 있습니다.";
        	String attachment = "html";
        	
        	// 첨부파일 파트 (합치는 과정 필요)
            MimeBodyPart attachPart = new MimeBodyPart();
            attachPart.setDataHandler(new DataHandler(attachment,"text/xml"));
            attachPart.setFileName("공항 정보 파일"); // 파일명
            
            //attachPart.setDataHandler(new DataHandler(new FileDataSource(new File("gmailSend.java"))));
            MimeBodyPart bodypart = new MimeBodyPart();
            bodypart.setContent(content, "text/html;charset=euc-kr");
           
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(bodypart);
            multipart.addBodyPart(attachPart);
           
            message.setContent(multipart);
            Transport.send(message);
            /*
            Transport.send(message);
            */
            
            System.out.println("Send Complete");
            
 
        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
	}

    public static void main(String[] args) {
    	GUI();
    }
 
}