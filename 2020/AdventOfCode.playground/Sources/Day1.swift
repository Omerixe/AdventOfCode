import Foundation
public struct Day1 {
    
    public init() {}
    
    public func part1() {
        let url = Bundle.main.url(forResource: "day1", withExtension: "txt")!
        let text = try! String(contentsOf: url)
        
        let numbers = text.split(separator: "\n")
            .map {
                Int($0) ?? 0
            }
            .sorted()
        
        for numberOne in numbers {
            for numberTwo in numbers {
                if numberOne + numberTwo == 2020 {
                    print(numberOne * numberTwo)
                    return
                }
            }
        }
    }
    
    public func part2() {
        let url = Bundle.main.url(forResource: "day1", withExtension: "txt")!
        let text = try! String(contentsOf: url)
        
        let numbers = text.split(separator: "\n")
            .map {
                Int($0) ?? 0
            }
            .sorted()
        
        for numberOne in numbers {
            for numberTwo in numbers {
                for numberThree in numbers {
                    if numberOne + numberTwo + numberThree == 2020 {
                        print(numberOne * numberTwo * numberThree)
                        return
                    }
                }
            }
        }
    }
}




