//
//  YoutubeVC.swift
//  FYEApp
//
//  Created by Malak Sadek on 7/18/17.
//  Copyright © 2017 Ibrahim Roshdy. All rights reserved.
//

import UIKit
import YouTubePlayer

class YoutubeVC: UIViewController {

    var code:String = ""
    
    @IBOutlet var videoPlayer: YouTubePlayerView!
    override func viewDidLoad() {
        super.viewDidLoad()
        
        let value = UIInterfaceOrientation.landscapeLeft.rawValue
        UIDevice.current.setValue(value, forKey: "orientation")
        self.navigationController?.navigationBar.isTranslucent = true
        self.tabBarController?.tabBar.isHidden = true
        
    //let playerFrame = CGRect(x: 0, y: 0, width: 100, height: 100)
    //let videoPlayer = YouTubePlayerView(frame: playerFrame)
    videoPlayer.loadVideoID(code)
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    open override var shouldAutorotate: Bool {
        get {
            return true
        }
    }
    
    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */

}
