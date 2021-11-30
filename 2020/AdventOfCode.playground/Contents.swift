import UIKit

var str = "Hello, playground"

public struct Day2 {
    
    public init() {}
    
    public func part1() {
        let url = Bundle.main.url(forResource: "day2", withExtension: "txt")!
        let text = try! String(contentsOf: url)
        
        let inputs = text.split(separator: "\n")
            .map { $0.split(separator: ":")}
        
        var valid = 0
        inputs.forEach { input in
            let rule = input[0]
            
            let detail = rule.split(separator: " ")
            let count = detail[0].split(separator: "-")
            let character = detail[1].replacingOccurrences(of: " ", with: "")
            
            let numberOfOcc = input[1].filter {String($0) == character}.count
            if numberOfOcc >= Int(count[0])! && numberOfOcc <= Int(count[1])! {
                valid += 1
            }
        }
        print(valid)
    }
    
    public func part2() {
        let url = Bundle.main.url(forResource: "day2", withExtension: "txt")!
        let text = try! String(contentsOf: url)
        
        let inputs = text.split(separator: "\n")
            .map { $0.split(separator: ":")}
        
        var valid = 0
        inputs.forEach { input in
            let rule = input[0]
            let value = String(input[1])
            
            let detail = rule.split(separator: " ")
            let count = detail[0].split(separator: "-")
            let character = detail[1].replacingOccurrences(of: " ", with: "")
            
            let firstChar = value[(Int(count[0])!)]
            let secondChar = value[(Int(count[1])!)]
            
            if (firstChar == character) ^ (secondChar == character) {
                valid += 1
            } else {
                print(rule)
                print(value)
                print(firstChar)
                print(secondChar)
            }
        }
        print(valid)
    }
    
    
}

Day2().part2()
